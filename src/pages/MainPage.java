package src.pages;

import src.core.Router;
import src.core.interfaces.Page;
import src.core.layout.Menu;

public class MainPage implements Page {
    @Override
    public void render(Router router) {
        Integer[] lockeds = {1};
        String[] options = {"Cadastrar usuário", "Listar usuários", "Informações"};
        
        Menu.header("Menu Principal");
        int option = Menu.getPageOption(options, lockeds, true);
        Menu.divider();

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
