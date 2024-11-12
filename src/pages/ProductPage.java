package src.pages;

import pretty.Router;
import pretty.interfaces.Page;
import pretty.layout.Menu;
import src.log.Log;
import src.products.Product;
import src.products.Products;

public class ProductPage implements Page {
    private Product product;

    public ProductPage(Product product) {
        this.product = product;
    };

    @Override
    public void render(Menu menu, Router router) {
        Log.print("Navigating to product page.");

        Products products = Products.getInstance();

        menu.header(product.getName());
        menu.push("Preço: " + product.getPrice());
        String[] typeOptions = {"Alimentos", "Eletrônicos", "Roupas", "Beleza", "Saúde"};
        menu.push("Categoria: " + typeOptions[product.getType().getCode()]);
        menu.push("");

        
    }
}
