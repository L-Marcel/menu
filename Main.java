import pretty.Router;
import src.ShutdownHook;
import src.pages.MainPage;

public class Main {
    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new ShutdownHook());
        Router router = Router.getInstance();
        router.start(new MainPage());
        System.exit(0);
    }
};