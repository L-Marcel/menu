package src.products;

import java.time.LocalDate;

public class ElectronicProduct extends Product {
    private LocalDate warranty;
    private String brand;

    public ElectronicProduct(String name, float price, LocalDate warranty, String brand) {
        super(name, price, ProductType.ELECTRONIC);
        this.warranty = warranty;
        this.brand = brand;
    };

    public LocalDate getWarranty() {
        return warranty;
    };

    public void setWarranty(LocalDate warranty) {
        this.warranty = warranty;
    };

    public String getBrand() {
        return brand;
    };

    public void setBrand(String brand) {
        this.brand = brand;
    };
}
