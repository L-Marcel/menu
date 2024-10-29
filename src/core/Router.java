package src.core;

import java.util.LinkedList;

import src.core.interfaces.Page;
import src.core.layout.Menu;

public class Router {
    private LinkedList<Page> history = new LinkedList<>();

    public void start(Page page) {
        if(history.isEmpty()) {
            Menu.start();
            history.addLast(page);
            update();
        };
    };

    private void update() {
        if(!history.isEmpty()) {
            Page current = history.getLast();
            Menu.cleanup();
            current.render(this);
        } else {
            Menu.end();
        };
    };

    public void navigate(Page route) {
        history.addLast(route);
        update();
    }

    public void back() {
        history.removeLast();
        update();
    }

    public void replace(Page route) {
        history.removeLast();
        history.addLast(route);
        update();
    }
}
