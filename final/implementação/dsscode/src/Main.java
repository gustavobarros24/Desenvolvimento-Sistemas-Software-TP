import dl.DiretorDAO;
import dl.UserDAO;
import ln.models.Diretor;
import ui.MainMenu;

public class Main {
    static private class SeedDatabase {
        UserDAO userDAO = UserDAO.getInstance();
        DiretorDAO diretorDAO = DiretorDAO.getInstance();

        public void run() {
            Diretor newDiretor = new Diretor("diretor", "diretor", "123456");
            diretorDAO.save(newDiretor);
            userDAO.save(newDiretor);
        }
    }

    public static void main(String[] args) throws Exception {
        new SeedDatabase().run();
        MainMenu mainMenu = new MainMenu();
        mainMenu.run();
    }
}
