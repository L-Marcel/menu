import pretty.Router;
import src.core.Storage;
import src.pages.MainPage;

public class Main {
    public static void main(String[] args) {
        Storage storage = new Storage();
        storage.start();

        while(true) {
            try {
                storage.store();
                Thread.sleep(2000);
            } catch (Exception e) {}
        }

        
        // Router router = Router.getInstance();
        // router.start(new MainPage());
    }
};

                  