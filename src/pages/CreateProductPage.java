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

public class CreateProductPage implements Page {
    @Override
    public void render(Menu menu, Router router) {
        Log.print("Navigating to create product page.");

        String[] typeOptions = {"Alimentos", "Eletrônicos", "Roupas", "Beleza", "Saúde"};

        menu.header("Cadastrar produto");
        int type = menu.getOption("Categoria: ", typeOptions);
        String name = menu.getString("Nome: ", (t) -> {
            if (t.length() < 3) throw new InvalidInput("Forneça pelo menos três letras.");
            else if (t.length() > 20) throw new InvalidInput("Forneça no máximo vinte letras.");
        });
        float price = menu.getFloat("Preço (em R$): ", (t) -> {
            if (t < 0) throw new InvalidInput("O preço deve ser menor ou igual a zero.");
        });

        if(type == ProductType.ELECTRONIC.getCode()) {
            String brand = menu.getString("Marca: ", (t) -> {
                if (t.length() < 3) throw new InvalidInput("Forneça pelo menos três letras.");
                else if (t.length() > 20) throw new InvalidInput("Forneça no máximo vinte letras.");
            });

            String warrantyString = menu.getString("Prazo de garantia: ", (t) -> {
                try {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    formatter.withLocale(Locale.forLanguageTag("pt_BR"));
                    LocalDate.parse(t, formatter);
                } catch (Exception e) {
                    throw new InvalidInput("Forneça uma data no formato dd/mm/yyyy.");
                };                
            });

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            formatter.withLocale(Locale.forLanguageTag("pt_BR"));
            LocalDate waranty = LocalDate.parse(warrantyString, formatter);
            menu.divider();
            boolean confirmation = menu.getPageConfirmation();
            if(confirmation) {
                Products products = Products.getInstance();
                products.add(new ElectronicProduct(name, price, waranty, brand));
            };
        } else {
            menu.divider();
            boolean confirmation = menu.getPageConfirmation();
            if(confirmation) {
                Products products = Products.getInstance();
                products.add(new Product(name, price, ProductType.fromCode(type)));
            };
        };

        router.back();
    }
}
