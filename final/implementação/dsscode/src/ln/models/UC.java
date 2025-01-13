package ln.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UC {
    private final int year;
    private final int semester;
    private final String code;
    private final String name;
    private String criterio = "";
    private final Map<String,Turno> turnos = new HashMap<>();

    public UC(int year, int semester, String code, String name) {
        this.year = year;
        this.semester = semester;
        this.code = code;
        this.name = name;
    }

    public UC(int year, int semester, String code, String name, String criterio) {
        this.year = year;
        this.semester = semester;
        this.code = code;
        this.name = name;
        this.criterio = criterio;
    }

    public int getYear() {
        return year;
    }

    public int getSemester() {
        return semester;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getTurnosVagos() {
        ArrayList<String> turnosVagos = new ArrayList<>();
        turnos.forEach((k,v) -> {
                    if (v.getQuantidadeDeInscritos() < v.getLotacao())
                        turnosVagos.add(k);
                }
        );
        return turnosVagos;
    }

    public Turno getTurno(String id) {
        return turnos.getOrDefault(id, null);
    }
}
