package ln.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Aluno extends User {
    private final String estatuto;
    private final int matricula;
    private final String nome;
    private final Map<String, UC> ucsinscritas;
    private List<Turno> horario;

    public Aluno(String email, String password, String estatuto, int matricula, String nome) {
        super(email, password, UserType.STUDENT);
        this.estatuto = estatuto;
        this.matricula = matricula;
        this.nome = nome;
        this.ucsinscritas = new HashMap<>();
        this.horario = new ArrayList<>();
    }

    public Aluno(String email, String password, String estatuto, int matricula, String nome, List<Turno> horario) {
        super(email, password, UserType.STUDENT);
        this.estatuto = estatuto;
        this.matricula = matricula;
        this.nome = nome;
        this.ucsinscritas = new HashMap<>();
        this.horario = horario;
    }

    public Aluno(String email, String password, String estatuto, int matricula, String nome, Map<String,UC> ucsinscritas) {
        super(email, password, UserType.STUDENT);
        this.estatuto = estatuto;
        this.matricula = matricula;
        this.nome = nome;
        this.ucsinscritas = ucsinscritas;
        this.horario = new ArrayList<>();
    }


    public String getNome() {
        return this.nome;
    }
    

    public Map<String, UC> getUcsinscritas() {
        return this.ucsinscritas;
    }

    @Override
    public String toString() {
        return "Aluno{" +
                "email='" + getEmail() + '\'' +
                ", password='" + getPassword() + '\'' +
                ", estatuto='" + estatuto + '\'' +
                ", matricula=" + matricula +
                ", nome='" + nome + '\'' +
                ", ucsinscritas=" + ucsinscritas +
                ", horario=" + horario +
                '}';
    }

    public UC getUCInscrita(String codigoUC) {
        return ucsinscritas.getOrDefault(codigoUC, null);
    }

    public List<Turno> getHorario() {
        return this.horario;
    }

    public boolean horarioCausaConflitoDeTipo(Turno turnoParaInscrever) {
        // Dado um turno de UC no qual se pretende inscrever, há conflito no horário quando
        // já se está inscrito noutro turno do mesmo tipo.
        // ex: tentar inscrever em TP1 sem prévia desinscrição do TP atual
        for (Turno t : horario) {
            if (t.mesmaUCmesmoTipo(turnoParaInscrever))
                return true;
        }
        return false;
    }

    public boolean estaInscrito(Turno turno) {
        for (Turno t : horario)
            if (t.equals(turno)) return true;
        return false;
    }

}
