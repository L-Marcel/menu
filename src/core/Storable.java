package src.core;

import java.io.Serializable;
import java.util.LinkedList;

import src.log.Log;

import java.io.FileInputStream;
import java.io.ObjectInputStream;

public abstract class Storable<T extends Serializable> implements Serializable {
    private Storage storage = Storage.getInstance();
    private String name;
    private LinkedList<T> instances;

    @SuppressWarnings("unchecked")
    public Storable(String name) {
        this.name = name;
        try {
            FileInputStream input = new FileInputStream("data/" + this.name + ".dat");  
            ObjectInputStream object = new ObjectInputStream(input);
            this.instances = (LinkedList<T>) object.readObject();
            Log.print("Storable", "Loadded " + this.instances.size() + " " + this.name + ".");
            object.close();         
        } catch (Exception e) {
            System.out.println(e);
            this.instances = new LinkedList<T>();
        };
    };

    public LinkedList<T> get() {
        return this.instances;
    };

    private void store() {
        storage.store(this.name, this.instances);
    };

    public void update() {
        store();
    };

    public void add(T t) {
        instances.add(t);
        store();
    };

    public void remove(T t) {
        instances.remove(t);
        store();
    };

    public void replace(T old, T updated) {
        int index = instances.indexOf(old);
        instances.set(index, updated);
        store();
    }
}
