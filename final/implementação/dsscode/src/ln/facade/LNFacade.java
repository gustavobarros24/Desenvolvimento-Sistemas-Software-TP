package ln.facade;

import java.util.List;

public class LNFacade {
    private final ISubUser subUserFacade = new SubUserFacade();
    private final DataImporterFacade dataImporterFacade = new DataImporterFacade();
    private final ISubAluno subAlunoFacade = new SubAlunoFacade();
    private final ISubDiretor subDiretorFacade = new SubDiretorFacade();

    public String authenticate(String email, String password) {
        return subUserFacade.authenticate(email, password);
    }

    public void endSession() {
        subUserFacade.endSession();
    }

    public void importData(String filePath, String fileType) {
        dataImporterFacade.importData(filePath, fileType);
    }

    public boolean findSchedule() {
        System.out.println("O seu horário é o seguinte:");
        return subAlunoFacade.findSchedule();
    }

    public boolean alunoExiste(String email) {
        return subAlunoFacade.findAluno(email) != null;
    }

    public boolean alunoMatriculadoEmUC(String email, String UCcode) {
        return subAlunoFacade.getSubscribedUC(email, UCcode) != null;
    }

    public List<String> findAvailableShifts(String UCcode) {
        return subDiretorFacade.findAvailableShiftsByUC(UCcode);
    }

    public boolean inscreverEmTurno(String email, String idTurno) {
        return subDiretorFacade.inscreverEmTurno(email, idTurno);
    }

    public List<String> inscritoEmTurnosDeUC(String email, String codigoUC) {
        return subAlunoFacade.getTurnosInscrito(email, codigoUC);
    }

    public boolean desinscreverDeTurno(String email, String idTurno) {
        return subDiretorFacade.desinscreverDeTurno(email, idTurno);
    }

    public void gerarHorario() {
        subDiretorFacade.makeSchedulesAuto();
    }

    public void apagarHorariosDosAlunos(){
        subDiretorFacade.apagarHorarios();
        subDiretorFacade.libetarvagasturnos();
    }
}
