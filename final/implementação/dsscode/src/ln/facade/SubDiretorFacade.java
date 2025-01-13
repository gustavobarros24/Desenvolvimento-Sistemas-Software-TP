package ln.facade;

import dl.AlunoDAO;
import dl.TurnoDAO;
import dl.UCDAO;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import ln.models.Aluno;
import ln.models.Turno;
import ln.models.UC;

public class SubDiretorFacade implements ISubDiretor {
    private final AlunoDAO alunoDAO = AlunoDAO.getInstance();
    private final UCDAO ucsDAO = UCDAO.getInstance();
    private final TurnoDAO turnoDAO = TurnoDAO.getInstance();

    @Override
    public List<String> findAvailableShiftsByUC(String UCcode) {
        List<Turno> turnosVagos = ucsDAO.findAvailableShifts(UCcode);

        // retorna só os IDs de cada turno vago encontrado
        return turnosVagos.stream()
                .map(Turno::getId)
                .toList();
    }

    @Override
    public boolean inscreverEmTurno(String email, String idTurno) {
        Aluno a = alunoDAO.findByEmail(email);
        Turno t = turnoDAO.findByID(idTurno);

        if (a == null || t == null)
            return false;
        if (a.horarioCausaConflitoDeTipo(t))
            return false;
        if (alunoDAO.inscreverEmTurno(email, idTurno) == false)
            return false;

        return true;
    }

    @Override
    public boolean desinscreverDeTurno(String email, String idTurno) {
        Aluno a = alunoDAO.findByEmail(email);
        Turno t = turnoDAO.findByID(idTurno);
        List<Turno> turnosinscrito = alunoDAO.getTurnosInscrito(email);
        if (a == null || t == null)
            return false;
        if (turnosinscrito.contains(t) == false)
            return false;
        if (alunoDAO.desinscreverDeTurno(email, idTurno) == false)
            return false;

        return true;
    }


    private boolean temConflito(Aluno aluno, Turno turno) {
        return aluno.getHorario().stream().anyMatch(t -> t.getDia() == turno.getDia() && t.getHoras().equals(turno.getHoras()));
    }

    @Override
    public void makeSchedulesAuto() {
        // Obter todos os alunos e turnos
        List<Aluno> alunos = alunoDAO.getTodosOsAlunos();
        List<Turno> turnos = turnoDAO.getTodosOsTurnos();

        // Organizar os turnos por UC
        Map<String, List<Turno>> turnosPorUC = turnos.stream()
                .collect(Collectors.groupingBy(Turno::getCodigoUC));

        for (Aluno aluno : alunos) {
            // Obter as UCs inscritas pelo aluno
            Map<String, UC> ucsInscritas = aluno.getUcsinscritas();

            for (String codigoUC : ucsInscritas.keySet()) {
                List<Turno> turnosUC = turnosPorUC.get(codigoUC);
                if (turnosUC == null) {
                    System.out.println("Nenhum turno disponível para a UC " + codigoUC);
                    continue;
                }

                // Filtrar os turnos da UC por tipo T e TP
                Optional<Turno> turnoT = turnosUC.stream()
                        .filter(turno -> turno.getTipo().equals("T") && turno.getQuantidadeDeInscritos() < turno.getLotacao())
                        .findFirst();

                Optional<Turno> turnoTP = turnosUC.stream()
                        .filter(turno -> turno.getTipo().equals("TP") && turno.getQuantidadeDeInscritos() < turno.getLotacao())
                        .findFirst();

                boolean conflitoT = false;
                boolean conflitoTP = false;

                // Alocar turno T
                if (turnoT.isPresent()) {
                    Turno t = turnoT.get();
                    if (!temConflito(aluno, t)) {
                        aluno.getHorario().add(t);
                        t.setQuantidadeDeInscritos(t.getQuantidadeDeInscritos() + 1);
                    } else {
                        conflitoT = true;
                    }
                }

                // Alocar turno TP
                if (turnoTP.isPresent()) {
                    Turno tp = turnoTP.get();
                    if (!temConflito(aluno, tp)) {
                        aluno.getHorario().add(tp);
                        tp.setQuantidadeDeInscritos(tp.getQuantidadeDeInscritos() + 1);
                    } else {
                        conflitoTP = true;
                    }
                }

                if (conflitoT && conflitoTP) {
                    System.out.println("Incompatibilidade total para a UC " + codigoUC + " do aluno " + aluno.getNome());
                }

                if(conflitoT){
                    System.out.println("Incompatibilidade total para a UC " + codigoUC + " turno T do aluno " + aluno.getNome());
                }

                if(conflitoTP){
                    System.out.println("Incompatibilidade total para a UC " + codigoUC + " turno TP do aluno " + aluno.getNome());
                }
            }
        }

        for(Aluno aluno : alunos){
            String alunoEmail = aluno.getEmail();

            for(Turno turno : aluno.getHorario()){
                alunoDAO.registarHorario(alunoEmail,turno.getId());
                turnoDAO.updateQuantidadeInscritos(turno.getId(), turno.getQuantidadeDeInscritos());
            }
        }
    }


    @Override
    public void apagarHorarios(){
        alunoDAO.apagarTodosOsHorarios();
    }

    @Override
    public void libetarvagasturnos(){
        turnoDAO.libertartodasasvagas();
    }
}
