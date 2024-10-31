package src.pages;

import pretty.Router;
import pretty.interfaces.Page;
import pretty.layout.Menu;

public class MainPage implements Page {
    @Override
    public void render(Menu menu, Router router) {
        Integer[] lockeds = {1};
        String[] options = {"Cadastrar usuário", "Listar usuários", "Informações"};
        
        menu.header("Menu Principal");
        int option = menu.getPageOption(options, lockeds, true);
        menu.divider();

        switch(option) {
            case -1:
                router.back();
                break;
            case 0:
                router.navigate(new CreateUserPage());
                break;
            case 2:
                router.navigate(new InfomationPage());
                break;
            default:
                router.replace(this);
                break;
        }
    }
}
