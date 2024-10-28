package src.router.pages;

import src.errors.InvalidInput;
import src.interfaces.Page;
import src.layout.Menu;
import src.router.Router;

public class CreateUserPage implements Page {
    @Override
    public void render(Router router) {
        String[] access = {"Usuário", "Administrador"};
        String[] genrers = {"Masculino", "Feminino", "Outro"};

        Menu.header("Cadastrar usuário");
        Menu.getOption("Acesso: ", access);
        Menu.getOption("Gênero: ", genrers);
        Menu.getString("Nome: ", null);
        Menu.getString("E-mail: ", (t) -> {
            if (!t.contains("@")) throw new InvalidInput("Forneça um e-mail válido.");
        });
        Menu.getInt("Idade: ", (i) ->  {
            if (i < 18) throw new InvalidInput("O usuário deve ter pelo menos 18 anos.");
        });
        Menu.getFloat("Peso: ", null);
        Menu.divider();
        int confirmation = Menu.getPageConfirmation();
        switch(confirmation) {
            case -1:
                router.back();
                break;
            default:
                router.back();
                break;
        }
    }
}
