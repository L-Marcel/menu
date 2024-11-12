package src.pages;

import pretty.Router;
import pretty.interfaces.Page;
import pretty.layout.Menu;
import src.log.Log;
import src.products.Products;

public class ProductsPage implements Page {
    @Override
    public void render(Menu menu, Router router) {
        Log.print("Navigating to products page.");

        Products products = Products.getInstance();

        menu.header("Produtos");
        int selected = menu.getPageOption(
            products.get().stream().map(p -> p.toString()).toArray(String[]::new), 
            0, 5, 0, false
        );

        if (selected != -1) {
            router.navigate(new ProductPage(products.get().get(selected)));
        } else {
            router.back();
        };
    }
}
