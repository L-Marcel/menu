import pretty.Router;
import src.Test;
import src.Tests;
import src.core.Storage;
import src.pages.MainPage;

public class Main {
    public static void main(String[] args) {
        Tests tests = new Tests();
        if(tests.get().size() == 0) {
            System.out.println("Adding tests...");
            tests.add(new Test("Lucas"));
            for(int i = 0; i < 1000; i++) {
                tests.add(new Test("Test " + 1));
            };
            System.out.println("Finished...");
            System.err.println("Try re-run this application!");
        } else {
            System.out.println("Loading tests...");
            for(Test test : tests.get()) {
                System.out.println(test);
            };
        };
        // Router router = Router.getInstance();
        // router.start(new MainPage());
    }
};

                  