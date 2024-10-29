package src.pages;

import src.core.Router;
import src.core.interfaces.Page;
import src.core.layout.Menu;
import src.core.layout.Text;

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
