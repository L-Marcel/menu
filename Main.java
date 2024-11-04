import pretty.Router;
import src.ShutdownHook;
import src.Test;
import src.Tests;
import src.pages.MainPage;

public class Main {
    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new ShutdownHook());

        Tests tests = new Tests();

        if(tests.get().size() == 0) {
            for(int i = 0; i < 10; i++) {
                tests.add(new Test("Test " + i));
            };
        };

        Router router = Router.getInstance();
        router.start(new MainPage());

        if(tests.get().size() == 10) {
            for(int i = 10; i < 20; i++) {
                tests.add(new Test("Test " + i));
            };
        };

        System.exit(0);
    }
};

                  