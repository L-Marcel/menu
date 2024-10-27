package src;

import java.util.LinkedList;
import java.util.Scanner;

import src.errors.InvalidInput;


public class Menu {
    private static Scanner scan = new Scanner(System.in);
    private static LinkedList<String> lines = new LinkedList<String>();
    
    public static void push(String line) {
        lines.addLast(line);
        print(true);
    }

    public static void push(String line, Boolean newLine) {
        lines.addLast(line);
        print(newLine);
    }

    public static void rollback() {
        lines.removeLast();
        print(true);
    }

    public static void cleanup() {
        lines.clear();
    };

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

    public static void warning(String message) {
        clear();
        System.out.println(message);
        System.out.println("Pressione ENTER para continuar...");
        scan.nextLine();
    };

    public static int getInt(String prompt, Validator<Integer> validator) {
        try {
            push(prompt, false);
            int input = Integer.parseInt(scan.nextLine());
            if(validator != null) validator.validate(input);
            rollback();
            push(prompt + input);
            return input;
        } catch (NumberFormatException e) {
            warning("Por favor, forneça um número inteiro.");
            rollback();
            return getInt(prompt, validator);
        } catch (InvalidInput e) {
            warning(e.getMessage());
            rollback();
            return getInt(prompt, validator);
        } catch (Exception e) {
            rollback();
            return getInt(prompt, validator);
        }
    }

    public static double getDouble(String prompt, Validator<Double> validator) {
        try {
            push(prompt, false);
            double input = Double.parseDouble(scan.nextLine());
            if(validator != null) validator.validate(input);
            rollback();
            push(prompt + input);
            return input;
        } catch (NumberFormatException e) {
            warning("Por favor, forneça um número.");
            rollback();
            return getDouble(prompt, validator);
        } catch (InvalidInput e) {
            warning(e.getMessage());
            rollback();
            return getDouble(prompt, validator);
        } catch (Exception e) {
            rollback();
            return getDouble(prompt, validator);
        }
    }

    public static float getFloat(String prompt, Validator<Float> validator) {
        try {
            push(prompt, false);
            float input = Float.parseFloat(scan.nextLine());
            if(validator != null) validator.validate(input);
            rollback();
            push(prompt + input);
            return input;
        } catch (NumberFormatException e) {
            warning("Por favor, forneça um número.");
            rollback();
            return getFloat(prompt, validator);
        } catch (InvalidInput e) {
            warning(e.getMessage());
            rollback();
            return getFloat(prompt, validator);
        } catch (Exception e) {
            rollback();
            return getFloat(prompt, validator);
        }
    }

    public static char getChar(String prompt, Validator<Character> validator) {
        try {
            push(prompt, false);
            char input = scan.nextLine().trim().charAt(0);
            if(validator != null) validator.validate(input);
            rollback();
            push(prompt + input);
            return input;
        } catch (InvalidInput e) {
            warning(e.getMessage());
            rollback();
            return getChar(prompt, validator);
        } catch (Exception e) {
            rollback();
            return getChar(prompt, validator);
        }
    }

    public static String getString(String prompt, Validator<String> validator) {
        try {
            push(prompt, false);
            String input = scan.nextLine().trim();
            if(validator != null) validator.validate(input);
            else if (input.isEmpty()) throw new Exception();
            rollback();
            push(prompt + input);
            return input;
        } catch (InvalidInput e) {
            warning(e.getMessage());
            rollback();
            return getString(prompt, validator);
        } catch (Exception e) {
            rollback();
            return getString(prompt, validator);
        }
    }
}
