package src.utils;

import scala.tools.jline.console.ConsoleReader;
import src.layout.Menu;

public class Terminal {
    private ConsoleReader consoleReader;
    private Boolean isWindows = false;

    public Terminal() {
        try {
            consoleReader = new ConsoleReader();
            String os = System.getProperty("os.name").toLowerCase();
            if(os.startsWith("windows")) isWindows = true;
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
            int key = consoleReader.readVirtualKey();
            if(isWindows && key == 13) return 10;
            else return key;
        } catch (Exception e) {
            return -1;
        }
    };
}
