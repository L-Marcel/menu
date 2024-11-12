package src.pages;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import pretty.Router;
import pretty.errors.InvalidInput;
import pretty.interfaces.Page;
import pretty.layout.Menu;
import src.log.Log;
import src.products.ElectronicProduct;
import src.products.Product;
import src.products.Products;

public class EditProductPage implements Page {
    private Product product;

    public EditProductPage(Product product) {
        this.product = product;
    };

    @Override
    public void render(Menu menu, Router router) {
        Log.print("Navigating to edit product page.");

        if (product instanceof ElectronicProduct)
            menu.header("Editando produto eletrônico");
        else {
            menu.header("Editando produto");
        };

        menu.push("Nome antigo: " + this.product.getName());
        String newName = menu.getString("Novo nome: ", (t) -> {
            if (t.length() < 3) throw new InvalidInput("Forneça pelo menos três letras.");
            else if (t.length() > 20) throw new InvalidInput("Forneça no máximo vinte letras.");
        });
;   

        menu.push("Preço antigo (em R$): " + product.getPrice());
        float newPrice = menu.getFloat("Preço (em R$): ", (t) -> {
            if (t < 0) throw new InvalidInput("O preço deve ser menor ou igual a zero.");
        });

        if (product instanceof ElectronicProduct) {
            ElectronicProduct electronicProduct = (ElectronicProduct) product;
            
            menu.push("Marca antiga: " + electronicProduct.getBrand());
            String newBrand = menu.getString("Marca: ", (t) -> {
                if (t.length() < 3) throw new InvalidInput("Forneça pelo menos três letras.");
                else if (t.length() > 20) throw new InvalidInput("Forneça no máximo vinte letras.");
            });

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            formatter.withLocale(Locale.forLanguageTag("pt_BR"));
            menu.push("Prazo de validade antigo: " + electronicProduct.getWarranty().format(formatter));
            String warrantyString = menu.getString("Prazo de garantia: ", (t) -> {
                try {
                    LocalDate.parse(t, formatter);
                } catch (Exception e) {
                    throw new InvalidInput("Forneça uma data no formato dd/mm/yyyy.");
                };                
            });

            LocalDate newWarranty = LocalDate.parse(warrantyString, formatter);
            menu.divider();
            boolean confirmation = menu.getPageConfirmation();
            if(confirmation) {
                electronicProduct.setName(newName);
                electronicProduct.setPrice(newPrice);
                electronicProduct.setBrand(newBrand);
                electronicProduct.setWarranty(newWarranty);
            };
        } else {
            String[] typeOptions = {"Alimentos", "Roupas", "Beleza", "Saúde"};

            menu.divider();
            boolean confirmation = menu.getPageConfirmation();
            if(confirmation) {
                product.setName(newName);
                product.setPrice(newPrice);
                //product.setType(newType);
            };
        }
    };
}
