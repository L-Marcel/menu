package src.pages;

import pretty.Router;
import pretty.interfaces.Page;
import pretty.layout.Menu;
import pretty.layout.Text;
import src.log.Log;

public class InfomationPage implements Page {
    @Override
    public void render(Menu menu, Router router) {
        Log.print("Navigating to information page.");
        menu.header("Informações");
        menu.push("Versão: " + Text.highlight("1.0.0"));
        menu.push("Desenvolvido por: " + Text.highlight("Lucas Marcel"));
        menu.divider();
        menu.pushPageBack();
        router.back();
    }
}
