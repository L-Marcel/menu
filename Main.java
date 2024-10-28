import src.router.Router;
import src.router.pages.MainPage;

public class Main {
    public static void main(String[] args) {
        Router router = new Router();
        router.start(new MainPage());
    }
};

                  