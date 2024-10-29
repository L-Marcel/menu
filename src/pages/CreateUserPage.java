package src.pages;

import console.Router;
import console.errors.InvalidInput;
import console.interfaces.Page;
import console.layout.Menu;

public class CreateUserPage implements Page {
    @Override
    public void render(Menu menu, Router router) {
        String[] access = {"Usuário", "Administrador"};
        String[] genrers = {"Masculino", "Feminino", "Outro"};

        menu.header("Cadastrar usuário");
        menu.getOption("Acesso: ", access);
        menu.getOption("Gênero: ", genrers);
        menu.getString("Nome: ", null);
        menu.getString("E-mail: ", (t) -> {
            if (!t.contains("@")) throw new InvalidInput("Forneça um e-mail válido.");
        });
        menu.getInt("Idade: ", (i) ->  {
            if (i < 18) throw new InvalidInput("O usuário deve ter pelo menos 18 anos.");
        });
        menu.getFloat("Peso: ", null);
        menu.divider();
        menu.getPageConfirmation();
        router.back();
    }
}
