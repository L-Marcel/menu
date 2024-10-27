import src.Menu;
import src.errors.InvalidInput;

public class Main {
    public static void main(String[] args) {
        Menu.push("========== Cadastro ==========");
        int a = Menu.getInt("Int: ", (i) ->  {
            if (i < 0) {
                throw new InvalidInput("Por favor, um número positivo.");
            }
        });
        float b = Menu.getFloat("Float: ", null);
        double c = Menu.getDouble("Double: ", null);
        char d = Menu.getChar("Char: ", null);
        String e = Menu.getString("String: ", null);
        Menu.cleanup();
        Menu.push(a + " " + b + " " + c + " " + d + " " + e);
    }
};

                  