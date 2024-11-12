package src.pages;

import pretty.Router;
import pretty.interfaces.Page;
import pretty.layout.Menu;
import src.log.Log;
import src.products.Products;

public class MainPage implements Page {
    @Override
    public void render(Menu menu, Router router) {
        Log.print("Navigating to main page.");
        
        Products products = Products.getInstance();
        Integer[] lockeds = {1};
        String[] options = {
            "Cadastrar um produto",
            "Buscar produtos",
            "Listar produtos por categoria",
            "Listar todos os produtos",
            "Informações"
        };
        
        int option = -1;
        menu.header("Menu Principal");
        if (products.get().size() == 0) {
            option = menu.getPageOption(options, lockeds, true);
        } else {
            option = menu.getPageOption(options, true);
        };
        menu.divider();

        switch(option) {
            case -1:
                router.back();
                break;
            case 0:
                router.navigate(new CreateProductPage());
                break;
            case 1:
                router.navigate(new ProductsPage(true, false));
                break;
            case 2:
                router.navigate(new ProductsPage());
                break;
            case 3:
                router.navigate(new ProductsPage(false, true));
                break;
            case 4:
                router.navigate(new InfomationPage());
                break;
            default:
                router.replace(this);
                break;
        };
    };
}
