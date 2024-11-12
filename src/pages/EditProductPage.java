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
import src.products.ProductType;
import src.products.Products;

public class EditProductPage implements Page {
    private Product product;

    public EditProductPage(Product product) {
        this.product = product;
    };

    @Override
    public void render(Menu menu, Router router) {
        Log.print("Navigating to edit product page.");

        menu.header("Editando produto");

        menu.push("Nome antigo: " + this.product.getName());
        String newName = menu.getString("Novo nome: ", (t) -> {
            if (t.length() < 3) throw new InvalidInput("Forneça pelo menos três letras.");
            else if (t.length() > 20) throw new InvalidInput("Forneça no máximo vinte letras.");
        });

        menu.push("Preço antigo (em R$): " + product.getPrice());
        float newPrice = menu.getFloat("Novo preço (em R$): ", (t) -> {
            if (t < 0) throw new InvalidInput("O preço deve ser menor ou igual a zero.");
        });

        String[] typeOptions = {"Alimentos", "Eletrônicos", "Roupas", "Beleza", "Saúde"};
        menu.push("Categoria antiga: " + typeOptions[this.product.getType().getCode()]);
        int type = menu.getOption("Nova categoria: ", typeOptions);
       
        if (type == ProductType.ELECTRONIC.getCode()) {
            boolean alreadyElectronic = product instanceof ElectronicProduct;
           
            if (alreadyElectronic) {
                ElectronicProduct electronicProduct = (ElectronicProduct) product;
                menu.push("Marca antiga: " + electronicProduct.getBrand());
            };

            String newBrand = menu.getString("Nova marca: ", (t) -> {
                if (t.length() < 3) throw new InvalidInput("Forneça pelo menos três letras.");
                else if (t.length() > 20) throw new InvalidInput("Forneça no máximo vinte letras.");
            });

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            formatter.withLocale(Locale.forLanguageTag("pt_BR"));

            if (alreadyElectronic) {
                ElectronicProduct electronicProduct = (ElectronicProduct) product;
                menu.push("Prazo de validade antigo: " + electronicProduct.getWarranty().format(formatter));
            };

            String warrantyString = menu.getString("Novo prazo de garantia: ", (t) -> {
                try {
                    LocalDate.parse(t, formatter);
                } catch (Exception e) {
                    throw new InvalidInput("Forneça uma data no formato dd/mm/yyyy.");
                };                
            });
            LocalDate newWarranty = LocalDate.parse(warrantyString, formatter);
            menu.divider();
            boolean confirmation = menu.getPageConfirmation();

            if (alreadyElectronic && confirmation) {
                ElectronicProduct electronicProduct = (ElectronicProduct) product;
                Products products = Products.getInstance();
                electronicProduct.setName(newName);
                electronicProduct.setPrice(newPrice);
                electronicProduct.setBrand(newBrand);
                electronicProduct.setWarranty(newWarranty);
                products.update();
            } else if (confirmation) {
                Products products = Products.getInstance();
                ElectronicProduct electronicProduct  = new ElectronicProduct(newName, newPrice, newWarranty, newBrand);
                products.replace(product, electronicProduct);
            };
        } else {
            menu.divider();
            boolean confirmation = menu.getPageConfirmation();

            if (confirmation) {
                Products products = Products.getInstance();
                Product updatedProduct = new Product(newName, newPrice, ProductType.fromCode(type));
                products.replace(product, updatedProduct);
            };
        };

        router.back();
    };
}
