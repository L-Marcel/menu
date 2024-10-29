package src.core.keys;

public enum Key {
    UNTRANSLATABLE("Untranslatable"),
    ENTER("Enter"),
    BACKSPACE("Backspace"),
    SPACE("Space"),
    UP("Up"),
    DOWN("Down"),
    LEFT("Left"),
    RIGHT("Right");

    private String name;
    private int code = 0;

    private Key(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getCode() {
        return code;
    }

    public static Key untranslatable(int code) {
        UNTRANSLATABLE.code = code;
        return UNTRANSLATABLE;
    };

    @Override
    public String toString() {
        if(name.equals("Untranslatable")) return super.toString() + "(" + code + ")";
        return super.toString();
    }
}
