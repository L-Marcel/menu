package src.products;

import src.core.Storable;

public class Products extends Storable<Product> {
    private static Products instance;

    private Products() {
        super("products");
    };

    public static Products getInstance() {
        if (Products.instance == null) Products.instance = new Products();
        return Products.instance;
    };
}
