package ui;

import java.util.List;
import ln.facade.LNFacade;


public class DiretorMenu extends Menu {
    private final LNFacade lnFacade = new LNFacade(); // FIXME: deve posteriormente ser recebido como um parâmetro do método construturo e não ser inicializado diretamente aqui na classe

    @Override
    public void display() {
        System.out.println();
        System.out.println("-----------------------------");
        System.out.println(" 1 - Importar dados");
        System.out.println(" 2 - Gerar horários");
        System.out.println(" 3 - Desinscrever aluno manualmente");
        System.out.println(" 4 - Inscrever aluno manualmente");
        System.out.println(" 0 - Terminar sessão");
        System.out.println("-----------------------------");

    }

    @Override
    public void handleChoice(int choice) {
        switch(choice) {
            case 0:
                System.out.println("Terminando sessão...");
                lnFacade.endSession();
                break;
            case 1:
                importarDados();
                break;
            case 2:
                apagarOsHorarios();
                gerarHorario();
                break;
                
            case 3:
                desinscreverAlunoManualmente();
                break;
            case 4:
                inscreverAlunoManualmente();
                break;
            default:
                System.out.println("Opção inválida!");
        }
    }

    private void importarDados() {
        System.out.println("Caminho do ficheiro:");
        String filePath = this.scanner.nextLine();

        System.out.println("Tipo dos dados a importar (alunos, ucs, turnos):");
        String fileType = this.scanner.nextLine();

        lnFacade.importData(filePath, fileType);
    }


    private void gerarHorario(){
        System.out.println("O processo de distribuição de turnos começou.");
        lnFacade.gerarHorario();
    }

    private void apagarOsHorarios(){
        lnFacade.apagarHorariosDosAlunos();
    }

    private void desinscreverAlunoManualmente() {
        // Obter instância Aluno
        System.out.println("Email do Aluno:");
        String email = this.scanner.nextLine();
        if (!lnFacade.alunoExiste(email)) {
            System.out.println("Nenhum aluno tem esse email!");
            return;
        }

        // Obter instância UC
        System.out.println("Código da UC:");
        String codigoUC = this.scanner.nextLine();
        if (!lnFacade.alunoMatriculadoEmUC(email, codigoUC)) {
            System.out.println("O aluno não está inscrito nessa UC!");
            return;
        }

        // Mostrar turnos da UC em que aluno está inscrito
        System.out.println("Turnos da UC em que está inscrito:");
        List<String> turnosInscrito = lnFacade.inscritoEmTurnosDeUC(email, codigoUC);
        System.out.println(turnosInscrito.toString() + "\n");

        // Obter turno para desinscrição
        System.out.println("Turno:");
        String idTurno = this.scanner.nextLine();
        if (!turnosInscrito.contains(idTurno)) {
            System.out.println("O aluno não está inscrito nessa turno!");
            return;
        }

        boolean success = lnFacade.desinscreverDeTurno(email, idTurno);
        if (success) System.out.println("Sucesso na desinscrição!");
        else System.out.println("Insucesso na desinscrição.");
    }

    private void inscreverAlunoManualmente() {
        // Verificar se aluno existe
        System.out.println("Email do Aluno:");
        String email = this.scanner.nextLine();
        if (!lnFacade.alunoExiste(email)) {
            System.out.println("Nenhum aluno tem esse email!");
            return;
        }

        // Verificar se aluno está inscrito na UC
        System.out.println("Código da UC:");
        String codigoUC = this.scanner.nextLine();
        if (!lnFacade.alunoMatriculadoEmUC(email, codigoUC)) {
            System.out.println("O aluno não está inscrito nessa UC!");
            return;
        }

        // Mostrar turnos disponíveis para inscrição
        System.out.println("Turnos da UC com vagas:");
        List<String> turnosVagos = lnFacade.findAvailableShifts(codigoUC);
        System.out.println(turnosVagos.toString() + "\n");

        // Obter instância turno
        System.out.println("Turno:");
        String idTurno = this.scanner.nextLine();
        if (!turnosVagos.contains(idTurno)) {
            System.out.println("Tente de novo, a UC não tem esse turno!");
            return;
        }

        boolean success = lnFacade.inscreverEmTurno(email, idTurno);
        if (success) System.out.println("Sucesso na inscrição!");
        else System.out.println("Insucesso na inscrição: para esta UC, o aluno já está noutro turno do mesmo tipo.");
    }
}
