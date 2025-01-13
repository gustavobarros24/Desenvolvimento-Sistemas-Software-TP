package ln.facade;

import java.util.List;

public interface ISubDiretor {
    List<String> findAvailableShiftsByUC(String UCcode);
    boolean inscreverEmTurno(String email, String idTurno);
    boolean desinscreverDeTurno(String email, String idTurno);
    void makeSchedulesAuto();
    void apagarHorarios();
    void libetarvagasturnos();
}
