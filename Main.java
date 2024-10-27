import src.Menu;
import src.errors.InvalidInput;

public class Main {
    public static void main(String[] args) {
        Menu.header("Cadastro");
        Menu.getInt("Int: ", (i) ->  {
            if (i < 0) throw new InvalidInput("Por favor, um número positivo.");
        });
        Menu.getFloat("Float: ", null);
        Menu.getDouble("Double: ", null);
        Menu.getChar("Char: ", null);
        Menu.getString("String: ", null);
        Menu.divider();
    }
};

                  