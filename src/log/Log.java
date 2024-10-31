package src.log;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Semaphore;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Log extends Thread {
    private static Log instance;
    private Semaphore semaphore = new Semaphore(0);
    private boolean running = true;
    private ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<String>();;
    private FileWriter writer;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private Log() {
        super();
        setPriority(MIN_PRIORITY);
        try {
            Files.createDirectories(Paths.get("data"));
            File file = new File("data/log.txt");
            file.createNewFile();
            file.setWritable(true);
            this.writer = new FileWriter(file);
        } catch (Exception e) {};
    };

    public static Log getInstance() {
        if(instance == null) instance = new Log();
        return instance;
    };

    public synchronized void print(String string) {
        this.queue.add("[" + this.formatter.format(LocalDateTime.now()) + "]" + string);
        if(this.semaphore.availablePermits() == 0) this.semaphore.release();
    };

    @Override
    public void run() {
        while(running || !queue.isEmpty()) {
            try {
                if(queue.isEmpty()) this.semaphore.acquire();
                while(!queue.isEmpty()) {
                    String log = queue.poll();
                    writer.write(log);
                };
                this.writer.close();
            } catch (Exception e) {};
        };
    };
};