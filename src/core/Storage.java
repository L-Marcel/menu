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
    private Semaphore semaphore = new Semaphore(0);
    private static Storage instance;
    private boolean running = false;
    private ConcurrentHashMap<String, Integer> tasks = new ConcurrentHashMap<String, Integer>();
    private ConcurrentSkipListMap<String, LinkedList<Serializable>> map = new ConcurrentSkipListMap<String, LinkedList<Serializable>>(); 

    //#region Singleton
    private Storage() {
        super();
        setPriority(3);
        try {
            Files.createDirectories(Paths.get("data"));
        } catch (Exception e) {};
        this.start();
    };
    
    public static Storage getInstance() {
        if(Storage.instance == null) Storage.instance = new Storage();
        return Storage.instance;
    };
    //#endregion

    //#region Tasks
    public void check() {
        this.check(true);
    };

    public void check(boolean task) {
        if(this.semaphore.availablePermits() == 0) {
            this.semaphore.release();
            if(task) Log.print("Storage", "Tasks were released.");
        };
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
        } catch (Exception e) {
            Log.print("Storage", "Task request failed.");
        };
    };

    @Override
    public void run() {
        this.running = true;
        Log.print("Storage", "Started.");
        while(this.running || this.hasTask()) {
            try {
                if(!this.hasTask()) {
                    semaphore.acquire();
                };

                for(String name : this.map.keySet()) {
                    if(this.hasTask(name)) Thread.sleep(1000);
                    else continue;

                    Integer count = this.tasks.getOrDefault(name, 0);
                    if(count > 1) {
                        this.tasks.put(name, 1);
                        continue;
                    };
                    
                    LinkedList<Serializable> storable = this.map.get(name);
                    FileOutputStream out = new FileOutputStream("data/" + name + ".db");
                    ObjectOutputStream object = new ObjectOutputStream(out);
                    object.writeObject(storable);
                    object.flush();
                    object.close();
                    this.tasks.put(name, 0);
                    Log.print("Storage", "Task finished, " + name + " stored.");
                };
            } catch (Exception e) {
                Log.print("Storage", "Tasks failed.");
            };
        };
        Log.print("Storage", "Finished.");
    };
    //#endregion

    //#region Control
    public static void finish() {
        try {
            if(Storage.instance != null) {
                Log.print("Storage", "Finishing...");
                Storage.instance.running = false;
                Storage.instance.check(false);
                Storage.instance.join();
            };
        } catch (InterruptedException e) {
            Log.print("Storage", "Can't finish safely.");
        };
    };

    @Override
    public void start() {
        try {
            Log.print("Storage", "Starting...");
            super.start();
        } catch (IllegalThreadStateException e) {
            Log.print("Storage", "Can't start.");
        };
    };
    //#endregion
}
