package src;

import src.core.Storage;
import src.log.Log;

public class ShutdownHook extends Thread {
    @Override
    public void run() {
        System.out.println("\nEncerrando aplicação...");
        System.out.println("Sincronizando os dados salvos...");
        Storage.finish();
        Log.finish();
        System.out.println("Dados sincronizados com sucesso!");
    }
}
