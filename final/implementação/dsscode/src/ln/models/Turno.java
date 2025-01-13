package ln.models;

import java.time.LocalTime;
import java.util.Objects;

public class Turno {
    private final String id;
    private final String codigoUC;
    private final String sala;
    private final int lotacao;
    private final LocalTime horas;
    private final int dia;
    private final String tipo;
    private int quantidadeDeInscritos;

    public Turno(String id, String codigoUC, String sala, int lotacao,String tipo, LocalTime horas, int dia) {
        this.id = id;
        this.codigoUC = codigoUC;
        this.sala = sala;
        this.lotacao = lotacao;
        this.tipo = tipo;
        this.horas = horas;
        this.dia = dia;
        this.quantidadeDeInscritos = 0;
    }

    public Turno(String id, String codigoUC, String sala, int lotacao,String tipo, LocalTime horas, int dia, int quantidadeDeInscritos) {
        this.id = id;
        this.codigoUC = codigoUC;
        this.sala = sala;
        this.lotacao = lotacao;
        this.tipo = tipo;
        this.horas = horas;
        this.dia = dia;
        this.quantidadeDeInscritos = quantidadeDeInscritos;
    }

    public String getId() {
        return id;
    }

    public String getCodigoUC() {
        return codigoUC;
    }

    public String getSala() {
        return sala;
    }

    public int getLotacao() {
        return lotacao;
    }

    public LocalTime getHoras() {
        return horas;
    }

    public int getDia() {
        return this.dia;
    }
    
    public String getTipo() {
        return tipo;
    }

    public int getQuantidadeDeInscritos() {
        return quantidadeDeInscritos;
    }

    public void setQuantidadeDeInscritos(int quantidadeDeInscritos) {
        this.quantidadeDeInscritos = quantidadeDeInscritos;
    }

    public boolean mesmaUCmesmoTipo(Turno outroTurno) {
        boolean mesmaUC = codigoUC.equals(outroTurno.codigoUC);
        boolean mesmoTipo = tipo.equals(outroTurno.getTipo());
        return mesmaUC && mesmoTipo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Turno turno)) return false;
        return Objects.equals(getId(), turno.getId()) && Objects.equals(getCodigoUC(), turno.getCodigoUC());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCodigoUC());
    }
}
