package src.pages;

import java.util.stream.Stream;

import pretty.Router;
import pretty.errors.InvalidInput;
import pretty.interfaces.Page;
import pretty.layout.Menu;
import src.log.Log;
import src.products.Product;
import src.products.Products;

public class ProductsPage implements Page {
    private String search = "";
    private boolean query = false;
    private boolean all = false;

    public ProductsPage() {};
    public ProductsPage(boolean query, boolean all) {
        this.query = query;
        this.all = all;
    };

    @Override
    public void render(Menu menu, Router router) {
        Log.print("Navigating to products page.");

        Products products = Products.getInstance();
        String[] typeOptions = {"Alimentos", "Eletrônicos", "Roupas", "Beleza", "Saúde"};
        Product[] filteredProducts;
                        
        if (!query && !all) {
            menu.header("Produtos por categoria");
            int option = menu.getPageOption(typeOptions);
            if (option == -1) {
                router.back();
                return;
            };

            filteredProducts = products.get()
                .stream()
                .filter(p -> p.getType().getCode() == option)
                .toArray(Product[]::new);

            menu.cleanup();
            menu.header(typeOptions[option]);
        } else if (this.query) {
            if (search.isBlank()) {
                menu.cleanup();
                menu.header("Buscando produtos");

                this.search = menu.getString("Buscar por nome: ", (t) -> {
                    if (t.length() < 3) throw new InvalidInput("Forneça pelo menos três letras.");
                    if (t.length() > 20) throw new InvalidInput("Forneça no máximo vinte letras.");
                });
            }
            
            filteredProducts = products.get()
                .stream()
                .filter(p -> p.getName().toLowerCase().contains(this.search.toLowerCase()))
                .toArray(Product[]::new);
            
            menu.cleanup();
            menu.header("Produtos encontrados");
            menu.push("Busca: " + this.search);
            menu.divider();
        } else {
            filteredProducts = products.get()
                .stream()
                .toArray(Product[]::new);
            menu.header("Todos os produtos");
        };

        if (filteredProducts.length > 0) {
            String[] names = Stream.of(filteredProducts)
                .map(Product::getName)
                .toArray(String[]::new);

            int selected = menu.getPageOption(names, 0, 5, 0, false);

            switch(selected) {
                case -1:
                    router.back();
                    break;
                default:
                    router.navigate(new ProductPage(products.get().indexOf(filteredProducts[selected])));
                    break;
            };
        } else {
            menu.push("Nenhum produto encontrado.");
            menu.divider();
            menu.pushPageBack();
            if (!query && !all) router.replace(this);
            else router.back();
        };
    };
}
