package src.core;

import java.util.concurrent.Semaphore;

public class Storage extends Thread {
    private Semaphore semaphore = new Semaphore(0);
    
    public void store() {
        semaphore.release();
    };

    @Override
    public void run() {
        while(true) {
            try {
                semaphore.acquire();
                System.out.println("Running...");
            } catch (InterruptedException e) {
                this.interrupt();
            }
        }
    };
}
