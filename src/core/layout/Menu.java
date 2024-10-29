package src.core.layout;

import java.util.LinkedList;

import src.core.Terminal;
import src.core.interfaces.Validator;
import src.core.keys.Key;
import src.core.utils.Array;
import src.errors.EmptyArray;
import src.errors.InvalidInput;

public class Menu {
    private static final int WIDTH = 40;
    private static Terminal<Key> terminal = Terminal.init();
    private static LinkedList<String> lines = new LinkedList<String>();
    private static int temporary = 0;

    public static void start() {
        terminal.start();
    };

    public static void end() {
        terminal.end();
    };

    //#region Control
    public static void push(String line) {
        lines.addLast(line);
        print(true);
    }

    public static void push(String line, boolean newLine) {
        lines.addLast(line);
        print(newLine);
    }

    public static void temporarilyPush(String line) {
        temporary++;
        push(line);
    }

    public static void phantomPush(String line) {
        lines.addLast(line);
    }

    public static void rollback() {
        rollback(temporary + 1);
        temporary = 0;
    }

    public static void rollback(int count) {
        while(count > 0) {
            lines.removeLast();
            count--;
        }
        print(true);
    }

    public static void rollbackKeepingTemporary() {
        lines.removeLast();
        print(true);
    }

    public static void cleanup() {
        lines.clear();
    };
    //#endregion

    //#region Private
    private static void clear() {
        terminal.clear();
    };

    private static void print(boolean newLine) {
        clear();
        for (int i = 0; i < lines.size(); i++) {
            if(i == lines.size() - 1 && !newLine) {
                System.out.print(lines.get(i));
            } else {
                System.out.println(lines.get(i));
            }
        }
    }
    //#endregion

    //#region Special
    public static void warning(String message) {
        warning(message, 1);
    };

    public static void warning(String message, int rollbacks) {
        rollback(temporary + rollbacks);
        temporary = 0;
        temporarilyPush("");
        temporarilyPush(Text.warning(">> " + message));
        temporarilyPush("");
    };

    public static void header(String message) {
        int length = message.length() + 2;
        int space = (WIDTH - 6) - length;
        int left = Math.floorDiv(space, 2);
        int right = space - left;
        push("##" + "=".repeat(left) + "# " + Text.header(message) + " #" + "=".repeat(right) + "##");
    }

    public static void divider() {
        int space = WIDTH - 4;
        push("##" + "=".repeat(space) + "##");
    }
    //#endregion

    //#region Input
    public static int getInt(String prompt, Validator<Integer> validator) {
        try {
            String line = terminal.nextLine("- " + prompt);
            if(line.isEmpty()) throw new Exception();
            int input = Integer.parseInt(line);
            if(validator != null) validator.validate(input);
            rollback();
            push(Text.success("+ ") + prompt + Text.highlight(input));
            return input;
        } catch (NumberFormatException e) {
            warning("Por favor, forneça um número inteiro.");
            return getInt(prompt, validator);
        } catch (InvalidInput e) {
            warning(e.getMessage());
            return getInt(prompt, validator);
        } catch (Exception e) {
            rollbackKeepingTemporary();
            return getInt(prompt, validator);
        }
    }

    public static double getDouble(String prompt, Validator<Double> validator) {
        try {
            String line = terminal.nextLine("- " + prompt);
            if(line.isEmpty()) throw new Exception();
            double input = Double.parseDouble(line);
            if(validator != null) validator.validate(input);
            rollback();
            push(Text.success("+ ") + prompt + Text.highlight(input));
            return input;
        } catch (NumberFormatException e) {
            warning("Por favor, forneça um número.");
            return getDouble(prompt, validator);
        } catch (InvalidInput e) {
            warning(e.getMessage());
            return getDouble(prompt, validator);
        } catch (Exception e) {
            rollbackKeepingTemporary();
            return getDouble(prompt, validator);
        }
    }

    public static float getFloat(String prompt, Validator<Float> validator) {
        try {
            String line = terminal.nextLine("- " + prompt);
            if(line.isEmpty()) throw new Exception();
            float input = Float.parseFloat(line);
            if(validator != null) validator.validate(input);
            rollback();
            push(Text.success("+ ") + prompt + Text.highlight(input));
            return input;
        } catch (NumberFormatException e) {
            warning("Por favor, forneça um número.");
            return getFloat(prompt, validator);
        } catch (InvalidInput e) {
            warning(e.getMessage());
            return getFloat(prompt, validator);
        } catch (Exception e) {
            rollbackKeepingTemporary();
            return getFloat(prompt, validator);
        }
    }

    public static char getChar(String prompt, Validator<Character> validator) {
        try {
            char input = terminal.nextLine("- " + prompt).charAt(0);
            if(validator != null) validator.validate(input);
            rollback();
            push(Text.success("+ ") + prompt + Text.highlight(input));
            return input;
        } catch (InvalidInput e) {
            warning(e.getMessage());
            return getChar(prompt, validator);
        } catch (Exception e) {
            rollbackKeepingTemporary();
            return getChar(prompt, validator);
        }
    }

    public static String getString(String prompt, Validator<String> validator) {
        try {
            String input = terminal.nextLine("- " + prompt);
            if(input.isEmpty()) throw new Exception();
            else if(validator != null) validator.validate(input);
            
            rollback();
            push(Text.success("+ ") + prompt + Text.highlight(input));
            return input;
        } catch (InvalidInput e) {
            warning(e.getMessage());
            return getString(prompt, validator);
        } catch (Exception e) {
            rollbackKeepingTemporary();
            return getString(prompt, validator);
        }
    }
    
    public static int getOption(String prompt, String[] options) {
        return getOption(prompt, options, 0);
    };

    public static int getOption(String prompt, String[] options, int selected) {
        if(options.length == -1) throw new EmptyArray("Sem opções disponíveis.");
        try {
            push("- " + prompt);
            for (int i = 0; i < options.length; i++) {
                if(selected == i) {
                    push(Text.highlight(" > " + options[i]), i == options.length - 1);
                } else {
                    push("   " + options[i], i == options.length - 1);
                }
            }
            divider();
            push("[" + Text.highlight("UP") + "/" + Text.highlight("DOWN") + "] Escolher");
            push("[" + Text.highlight("ENTER") + "] Confirmar");

            Key key = terminal.key();
            rollback(options.length + 4);
            switch (key) {
                case ENTER:
                    push(Text.success("+ ") + prompt + Text.highlight(options[selected]));
                    return selected;
                case DOWN:
                    if(selected == options.length - 1) return getOption(prompt, options, 0);
                    return getOption(prompt, options, selected + 1);
                case UP:
                    if(selected == 0) return getOption(prompt, options, options.length - 1);
                    return getOption(prompt, options, selected - 1);
                default:
                    return getOption(prompt, options, selected);
            }
        } catch (Exception e) {
            warning(e.getMessage());
            return getOption(prompt, options);
        }
    }
    //#endregion

    //#region Page
    public static void pushPageBack() {
        push("[" + Text.highlight("BACKSPACE") + "] Voltar");
        if(terminal.key() != Key.BACKSPACE) {
            rollback(1);
            pushPageBack();
        }
    };

    public static boolean getPageConfirmation() {
        return getPageConfirmation(false);
    };

    public static boolean getPageConfirmation(boolean exit) {
        push("[" + Text.highlight("ENTER") + "] Confirmar");
        if(exit) push("[" + Text.highlight("BACKSPACE") + "] Sair");
        else push("[" + Text.highlight("BACKSPACE") + "] Voltar");

        Key key = terminal.key();
        switch (key) {
            case ENTER:
                return true;
            case BACKSPACE:
                return false;
            default:
                rollback(2);
                return getPageConfirmation(exit);
        }
    };

    public static int getPageOption(String[] options) {
        return getPageOption(options, null, 0, false);
    };

    public static int getPageOption(String[] options, boolean exit) {
        return getPageOption(options, null, 0, exit);
    };

    public static int getPageOption(String[] options, Integer[] lockeds) {
        return getPageOption(options, lockeds, 0, false);
    };

    public static int getPageOption(String[] options, Integer[] lockeds, boolean exit) {
        return getPageOption(options, lockeds, 0, exit);
    };

    public static int getPageOption(String[] options, Integer[] lockeds, int selected, boolean exit) {
        if(options == null || options.length == 0) {
            boolean confirmation = getPageConfirmation(exit);
            if(confirmation) return 0;
            else return -1;
        }
        else if(lockeds != null && lockeds.length >= options.length) selected = -1;

        try {
            for (int i = 0; i < options.length; i++) {
                if(Array.exists(lockeds, i)) {
                    push(Text.locked("- " + options[i]), i == options.length - 1);
                } else if(selected == i) {
                    push(Text.highlight("> " + options[i]), i == options.length - 1);
                } else {
                    push("- " + options[i], i == options.length - 1);
                }
            }
            divider();
            push("[" + Text.highlight("UP") + "/" + Text.highlight("DOWN") + "] Escolher");
            push("[" + Text.highlight("ENTER") + "] Confirmar");
            if(exit) push("[" + Text.highlight("BACKSPACE") + "] Sair");
            else push("[" + Text.highlight("BACKSPACE") + "] Voltar");

            Key key = terminal.key();
            switch (key) {
                case ENTER:
                    return selected;
                case BACKSPACE:
                    return -1;
                case DOWN:
                    rollback(options.length + 4);
                    if(selected >= 0) {
                        int next = (selected + 1) % options.length;
                        while(Array.exists(lockeds, next)) next = (next + 1) % options.length;
                        return getPageOption(options, lockeds, next, exit);
                    } else {
                        return getPageOption(options, lockeds, -1, exit);
                    }
                case UP:
                    rollback(options.length + 4);
                    if(selected >= 0) {
                        int previous = (((selected - 1) % options.length) + options.length) % options.length;
                        while(Array.exists(lockeds, previous)) previous = (((previous - 1) % options.length) + options.length) % options.length;
                        return getPageOption(options, lockeds, previous, exit);
                    } else {
                        return getPageOption(options, lockeds, -1, exit);
                    }
                default:
                    rollback(options.length + 4);
                    return getPageOption(options, lockeds, selected, exit);
            }
        } catch (Exception e) {
            warning(e.getMessage());
            return getPageOption(options, exit);
        }
    }
    //#endregion
}
