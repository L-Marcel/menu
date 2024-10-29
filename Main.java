import console.Router;
import src.pages.MainPage;

public class Main {
    public static void main(String[] args) {
        Router router = Router.getInstance();
        router.start(new MainPage());
    }
};

                  