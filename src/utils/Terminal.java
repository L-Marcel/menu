package src.utils;

import scala.tools.jline.console.ConsoleReader;
import src.layout.Menu;

public class Terminal {
    ConsoleReader consoleReader;

    public Terminal() {
        try {
            consoleReader = new ConsoleReader();
        } catch (Exception e) {
            consoleReader = null;
        }
    }

    public void clear() {
        try {
            consoleReader.clearScreen();
        } catch (Exception e) {
            System.out.print("\033[H\033[2J");
            System.out.flush();
        }
    };

    public String nextLine(String Terminal) {
        try {
            Menu.phantomPush(Terminal);
            return consoleReader.readLine(Terminal).trim();
        } catch (Exception e) {
            return "";
        }
    };

    public int key() {
        try {
            return consoleReader.readVirtualKey();
        } catch (Exception e) {
            return -1;
        }
    };
}
