package ui;

import ln.facade.LNFacade;

public class AlunoMenu extends Menu {
    private final LNFacade lnFacade = new LNFacade(); // FIXME: deve posteriormente ser recebido como um parâmetro do método construturo e não ser inicializado diretamente aqui na classe

    @Override
    public void display() {
        System.out.println();
        System.out.println("-----------------------------");
        System.out.println(" 1 - Consultar horário");
        System.out.println(" 0 - Terminar sessão");
        System.out.println("-----------------------------");
    }

    @Override
    public void handleChoice(int choice) {
        switch (choice) {
            case 0:
                System.out.println("Terminando sessão...");
                lnFacade.endSession();
                break;

            case 1:
                System.out.println("Consultando horário...");
                lnFacade.findSchedule();
                break;
        }
    }
}
