package ui;

import ln.facade.LNFacade;

public class MainMenu extends Menu {
    private final LNFacade lnFacade = new LNFacade(); // FIXME: deve posteriormente ser recebido como um parâmetro do método construturo e não ser inicializado diretamente aqui na classe

    public MainMenu() {
        super();
    }

    public MainMenu(Menu subMenu) {
        super(subMenu);
    }

    @Override
    public void display() {
        System.out.println();
        System.out.println("-----------------------------");
        System.out.println(" 1 - Iniciar sessão");
        System.out.println(" 0 - Sair");
        System.out.println("-----------------------------");
    }

    @Override
    public void handleChoice(int choice) {
        switch(choice) {
            case 0:
                System.out.println("Saindo...");
                break;
            case 1:
                System.out.println("Digite seu e-mail");
                String email = this.scanner.nextLine();

                System.out.println("Digite a palavra-passe");
                String password = this.scanner.nextLine();
                String menuType = lnFacade.authenticate(email, password);

                if (menuType.equals("diretor")) new DiretorMenu().run();
                else new AlunoMenu().run();
                break;

            default:
                System.out.println("Opção inválida!");
        }
    }
}
