package src.pages;

import src.core.Router;
import src.core.interfaces.Page;
import src.core.layout.Menu;
import src.errors.InvalidInput;

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
        Menu.getPageConfirmation();
        router.back();
    }
}
