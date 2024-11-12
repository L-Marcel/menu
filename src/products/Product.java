package src.products;

import java.io.Serializable;

public class Product implements Serializable  {
    private String name;
    private float price;
    private ProductType type;

    public Product(String name, float price, ProductType type) {
        this.name = name;
        this.price = price;
        this.type = type;
    };

    public String getName() {
        return name;
    };

    public void setName(String name) {
        this.name = name;
    };

    public float getPrice() {
        return price;
    };

    public void setPrice(float price) {
        this.price = price;
    };

    public ProductType getType() {
        return type;
    };

    public void setType(ProductType type) {
        this.type = type;
    };

    @Override
    public String toString() {
        return this.name + String.format(" (R$ %.2f)", this.price);
    };
}
