package src.core;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.LinkedList;
import java.io.Serializable;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Storage extends Thread {
    private static Storage instance;
    private boolean running = false;
    private ConcurrentHashMap<String, Integer> waits = new ConcurrentHashMap<String, Integer>();
    private ConcurrentSkipListMap<String, LinkedList<Serializable>> map = new ConcurrentSkipListMap<String, LinkedList<Serializable>>(); 

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
    
    @SuppressWarnings("unchecked")
    protected synchronized <T extends Serializable> void store(String name, LinkedList<T> data) {
        try {
            this.waits.merge(name, 1, Integer::sum);
            this.map.put(name, (LinkedList<Serializable>) data);
        } catch (Exception e) {};
    };

    public static void finish() {
        try {
            System.err.println("Finishing storage...");
            if(instance != null) {
                instance.running = false;
                instance.join();
            };
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
        int tasks = 0;
        while(running || !instance.waits.values().stream().allMatch((wait) -> wait == 0)) {
            try {
                for(String name : instance.map.keySet()) {
                    Thread.sleep(1000);

                    Integer count = instance.waits.getOrDefault(name, 0);
                    if(count > 1) {
                        instance.waits.put(name, 1);
                        continue;
                    };
                    
                    LinkedList<Serializable> storable = instance.map.get(name);
                    FileOutputStream out = new FileOutputStream("data/" + name + ".data");
                    ObjectOutputStream object = new ObjectOutputStream(out);
                    object.writeObject(storable);
                    object.flush();
                    object.close();
                    instance.waits.put(name, 0);
                    tasks++;
                    System.out.println("Storage task: " + tasks + " - size: " + storable.size());
                };
            } catch (Exception e) {};
        };
        
        System.out.println("Thread tasks: " + tasks);
    };
}
