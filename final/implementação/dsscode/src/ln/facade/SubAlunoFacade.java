package ln.facade;

import dl.AlunoDAO;
import dl.UCDAO;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import ln.models.Aluno;
import ln.models.Turno;
import ln.models.UC;
import ln.singleton.SessionManager;

public class SubAlunoFacade implements ISubAluno {
    private final AlunoDAO alunosDAO = AlunoDAO.getInstance();
    private final UCDAO ucsDAO = UCDAO.getInstance();

    @Override
    public Aluno findAluno(String email) {
        return alunosDAO.findByEmail(email);
    }

    @Override
    public UC getSubscribedUC(String email, String UCcode) {
        Map<String,UC> ucsinscritas = alunosDAO.getUCsFrequentadas(email);
        if(ucsinscritas.containsKey(UCcode)){
            return ucsinscritas.get(UCcode);
        }

        return null;
    }

    @Override
    public List<String> getTurnosInscrito(String email) {
        Aluno student = alunosDAO.findByEmail(email);
        if (student == null) {
            return null;
        }

        List<Turno> turnos = alunosDAO.getTurnosInscrito(email);

        return turnos.stream()
                .map(Turno::getId)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getTurnosInscrito(String email, String codigoUC) {
        Aluno student = alunosDAO.findByEmail(email);
        if (student == null) {
            return null;
        }

        UC uc = ucsDAO.findByCode(codigoUC);
        if (uc == null) {
            return null;
        }

        List<Turno> turnos = alunosDAO.getTurnosInscrito(email, codigoUC);

        return turnos.stream()
                .map(Turno::getId)
                .collect(Collectors.toList());
    }

    @Override
    public boolean hasSchedule() {
        String email = SessionManager.getInstance().getLoggedUserEmail();
        return alunosDAO.hasSchedule(email);
    }

    @Override
    public boolean findSchedule() {
        if (!this.hasSchedule()) {
            System.out.println("O horário ainda não está disponível. Tente mais tarde!");
            return false;
        }

        String email = SessionManager.getInstance().getLoggedUserEmail();
        boolean result = alunosDAO.findSchedule(email);
        return result;

    }

    @Override
    public boolean exportSchedule() {
        return false;
    }
}
