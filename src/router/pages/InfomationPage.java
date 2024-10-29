package src.router.pages;

import src.interfaces.Page;
import src.layout.Text;
import src.layout.Menu;
import src.router.Router;

public class InfomationPage implements Page {
    @Override
    public void render(Router router) {
        Menu.header("Informações");
        Menu.push("Versão: " + Text.highlight("1.0.0"));
        Menu.push("Desenvolvido por: " + Text.highlight("Lucas Marcel"));
        Menu.divider();
        Menu.pushPageBack();
        router.back();
    }
}
