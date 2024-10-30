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
            for(int i = 0; i < 100000; i++) {
                tests.add(new Test("Test " + i));
            };
            System.out.println("Finished...");
            System.err.println("Saving...");
        } else {
            System.out.println("Loading tests...");
            for(Test test : tests.get()) {
                System.out.println(test);
            };
        };

        Storage.finish();
        
        // Router router = Router.getInstance();
        // router.start(new MainPage());
    }
};

                  