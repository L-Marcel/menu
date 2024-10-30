package src;

import java.io.Serializable;

public class Test implements Serializable  {
    private String name;

    public Test(String name) {
        this.name = name;
    };

    @Override
    public String toString() {
        return this.name;
    };
}
