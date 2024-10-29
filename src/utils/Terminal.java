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

    public String nextLine(String terminal) {
        try {
            Menu.phantomPush(terminal);
            consoleReader.setPrompt(terminal);
            String line = consoleReader.readLine().trim();
            consoleReader.setPrompt("");
   
            return line;
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
