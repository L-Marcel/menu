package src.core;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.LinkedList;
import java.io.Serializable;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Storage extends Thread {
    private static Storage instance;
    private boolean running = false;
    private ConcurrentLinkedQueue<Packed> queue = new ConcurrentLinkedQueue<Packed>(); 

    private Storage() {
        try {
            Files.createDirectories(Paths.get("data"));
        } catch (Exception e) {};
        this.start();
    };

    public static Storage getInstance() {
        if(Storage.instance == null) Storage.instance = new Storage();
        return Storage.instance;
    };
    
    protected synchronized <T extends Serializable> void store(String name, LinkedList<T> data) {
        this.queue.add(new Packed(name, data));
        this.notify();
    };

    public void finish() {
        try {
            running = false;
            this.join();
        } catch (InterruptedException e) {};
    };

    @Override
    public void start() {
        try {
            super.start();
        } catch (IllegalThreadStateException e) {};
    };

    @Override
    public void run() {
        running = true;
        while(running || !queue.isEmpty()) {
            try {
                if(queue.isEmpty()) this.wait();
                Packed storable = queue.poll();
                FileOutputStream out = new FileOutputStream("data/" + storable.getName() + ".data");
                ObjectOutputStream object = new ObjectOutputStream(out);
                object.writeObject(storable);
                object.flush();
                object.close();
            } catch (Exception e) {};
        }
    };
}
