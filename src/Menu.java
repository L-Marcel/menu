package src;

import java.util.LinkedList;
import java.util.Scanner;

import src.errors.InvalidInput;

public class Menu {
    private static final int WIDTH = 40;
    private static Scanner scan = new Scanner(System.in);
    private static LinkedList<String> lines = new LinkedList<String>();
    private static int temporary = 0;

    //#region Control
    public static void push(String line) {
        lines.addLast(line);
        print(true);
    }

    public static void temporarilyPush(String line) {
        temporary++;
        push(line);
    }

    public static void push(String line, Boolean newLine) {
        lines.addLast(line);
        print(newLine);
    }

    public static void rollback() {
        while(temporary > 0) {
            lines.removeLast();
            temporary--;
        }
        lines.removeLast();
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
        System.out.print("\033[H\033[2J");
        System.out.flush();
    };

    private static void print(Boolean newLine) {
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
        rollback();
        temporarilyPush("");
        temporarilyPush(Format.warning("‣ " + message));
        temporarilyPush("");
    };

    public static void header(String message) {
        int length = message.length() + 2;
        int space = (WIDTH - 6) - length;
        int left = Math.floorDiv(space, 2);
        int right = space - left;
        push("##" + "=".repeat(left) + "# " + Format.header(message) + " #" + "=".repeat(right) + "##");
    }

    public static void divider() {
        int space = WIDTH - 4;
        push("##" + "=".repeat(space) + "##");
    }
    //#endregion

    //#region Input
    public static int getInt(String prompt, Validator<Integer> validator) {
        try {
            push("• " + prompt, false);
            String line = scan.nextLine().trim();
            if(line.isEmpty()) throw new Exception();
            int input = Integer.parseInt(line);
            if(validator != null) validator.validate(input);
            rollback();
            push(Format.success("✓ ") + prompt + Format.highlight(input));
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
            push("• " + prompt, false);
            String line = scan.nextLine().trim();
            if(line.isEmpty()) throw new Exception();
            double input = Double.parseDouble(line);
            if(validator != null) validator.validate(input);
            rollback();
            push(Format.success("✓ ") + prompt + Format.highlight(input));
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
            push("• " + prompt, false);
            String line = scan.nextLine().trim();
            if(line.isEmpty()) throw new Exception();
            float input = Float.parseFloat(line);
            if(validator != null) validator.validate(input);
            rollback();
            push(Format.success("✓ ") + prompt + Format.highlight(input));
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
            push("• " + prompt, false);
            char input = scan.nextLine().trim().charAt(0);
            if(validator != null) validator.validate(input);
            rollback();
            push(Format.success("✓ ") + prompt + Format.highlight(input));
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
            push("• " + prompt, false);
            String input = scan.nextLine().trim();
            if(validator != null) validator.validate(input);
            else if (input.isEmpty()) throw new Exception();
            rollback();
            push(Format.success("✓ ") + prompt + Format.highlight(input));
            return input;
        } catch (InvalidInput e) {
            warning(e.getMessage());
            return getString(prompt, validator);
        } catch (Exception e) {
            rollbackKeepingTemporary();
            return getString(prompt, validator);
        }
    }
    //#endregion
}
