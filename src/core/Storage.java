package src.core;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.Semaphore;

import src.log.Log;
import java.util.LinkedList;
import java.io.Serializable;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Storage extends Thread {
    private Log log = Log.getInstance();
    private Semaphore semaphore = new Semaphore(0);
    private static Storage instance;
    private boolean running = false;
    private ConcurrentHashMap<String, Integer> tasks = new ConcurrentHashMap<String, Integer>();
    private ConcurrentSkipListMap<String, LinkedList<Serializable>> map = new ConcurrentSkipListMap<String, LinkedList<Serializable>>(); 

    //#region Singleton
    private Storage() {
        super();
        try {
            Files.createDirectories(Paths.get("data"));
        } catch (Exception e) {};
        this.start();
        this.log.print("Starting storage thread...");
    };
    public static Storage getInstance() {
        if(Storage.instance == null) Storage.instance = new Storage();
        return Storage.instance;
    };
    //#endregion

    //#region Tasks
    public void check() {
        if(this.semaphore.availablePermits() == 0) this.semaphore.release();
    };

    public boolean hasTask() {
        return this.tasks.size() > 0 && this.tasks.values().stream().anyMatch((task) -> task != 0);
    };

    public boolean hasTask(String name) {
        return this.tasks.containsKey(name) && this.tasks.get(name) > 0;
    };
    //#endregion

    //#region Core
    @SuppressWarnings("unchecked")
    protected synchronized <T extends Serializable> void store(String name, LinkedList<T> data) {
        try {
            this.tasks.merge(name, 1, Integer::sum);
            this.map.put(name, (LinkedList<Serializable>) data);
            this.check();
        } catch (Exception e) {};
    };

    @Override
    public void run() {
        this.running = true;
        while(this.running || this.hasTask()) {
            try {
                if(!this.hasTask()) semaphore.acquire();
                for(String name : this.map.keySet()) {
                    if(this.hasTask(name)) Thread.sleep(1000);
                    else continue;

                    Integer count = this.tasks.getOrDefault(name, 0);
                    if(count > 1) {
                        this.tasks.put(name, 1);
                        continue;
                    };
                    
                    LinkedList<Serializable> storable = this.map.get(name);
                    FileOutputStream out = new FileOutputStream("data/" + name + ".data");
                    ObjectOutputStream object = new ObjectOutputStream(out);
                    object.writeObject(storable);
                    object.flush();
                    object.close();
                    this.tasks.put(name, 0);
                };
            } catch (Exception e) {};
        };
    };
    //#endregion

    //#region Control
    public static void finish() {
        try {
            if(instance != null) {
                instance.running = false;
                instance.check();
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
    //#endregion
}
