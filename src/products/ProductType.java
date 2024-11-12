package src.products;

public enum ProductType {
    FOOD(0),
    ELECTRONIC(1),
    CLOTHING(2),
    BEAUTY(3),
    HEALTH(4);

    private int code;

    private ProductType(int code) {
        this.code = code;
    }

    public static ProductType fromCode(int code) {
        for (ProductType type : ProductType.values()) {
            if (type.getCode() == code) return type;
        };

        return ProductType.FOOD;
    }; 

    public int getCode() {
        return this.code;
    };
}
