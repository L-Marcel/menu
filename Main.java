import src.router.Router;
import src.router.pages.MainPage;
import src.utils.Terminal;

public class Main {
    public static void main(String[] args) {
        Terminal terminal = new Terminal();
        while(true) {
            System.out.println(terminal.key());
        }
        // Router router = new Router();
        // router.start(new MainPage());
    }
};

                  