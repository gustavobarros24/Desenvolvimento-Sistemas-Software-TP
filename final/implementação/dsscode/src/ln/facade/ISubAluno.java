package ln.facade;

import java.util.List;
import ln.models.Aluno;
import ln.models.UC;

public interface ISubAluno {
    Aluno findAluno(String email);
    boolean hasSchedule();
    boolean findSchedule();
    boolean exportSchedule();
    UC getSubscribedUC(String email, String UCcode);
    List<String> getTurnosInscrito(String email);
    List<String> getTurnosInscrito(String email, String codigoUC);
}
