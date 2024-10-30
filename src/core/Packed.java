package src.core;

import java.io.Serializable;
import java.util.LinkedList;

public class Packed extends LinkedList<Serializable> {
    private String name;

    <T extends Serializable> Packed(String name, LinkedList<T> list) {
        super(list);
        this.name = name;
    };

    public String getName() {
        return this.name;
    };
}
