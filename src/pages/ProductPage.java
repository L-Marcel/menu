package src.pages;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

import pretty.Router;
import pretty.interfaces.Page;
import pretty.layout.Menu;
import src.log.Log;
import src.products.ElectronicProduct;
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

        menu.header(this.product.getName());
        menu.push("Preço: " + this.product.getPrice());
        String[] typeOptions = {"Alimentos", "Eletrônicos", "Roupas", "Beleza", "Saúde"};
        menu.push("Categoria: " + typeOptions[this.product.getType().getCode()]);
        if (product instanceof ElectronicProduct) {
            ElectronicProduct electronicProduct = (ElectronicProduct) product;
            menu.push("Marca: " + electronicProduct.getBrand());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            formatter.withLocale(Locale.forLanguageTag("pt_BR"));
            menu.push("Prazo de validade: " + electronicProduct.getWarranty().format(formatter));
        };

        String[] options = {
            "Editar produto",
            "Remover produto"
        };

        menu.divider();
        int option = menu.getPageOption(options);

        switch(option) {
            case -1:
                router.back();
                break;
            case 0:
                router.navigate(new EditProductPage(product));
                break;
            case 1:
                menu.header("Deseja mesmo removê-lo?");
                boolean confirmation = menu.getPageConfirmation();
                if (confirmation) {
                    products.remove(product);
                    router.back();
                } else {
                    router.replace(this);
                };
                break;
            default:
                router.replace(this);
                break;
        };
    };
}
