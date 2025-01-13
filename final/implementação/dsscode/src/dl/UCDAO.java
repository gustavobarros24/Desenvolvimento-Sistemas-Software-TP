package dl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ln.models.Turno;
import ln.models.UC;

public class UCDAO {
    private static UCDAO instance; // FIXME: Remover futuramente

    private final Map<String, UC> ucs; // FIXME: Remover futuramente

    public UCDAO() {
        this.ucs = new HashMap<>();
    }

    public static UCDAO getInstance() {
        if (instance == null) {
            instance = new UCDAO();
        }
        return instance;
    }

    public Boolean save(String code, int year, int semestre, String name, String criterio) {
         String query = "INSERT INTO UC (code, year, semester, name, criterio) VALUES (?, ?, ?, ?, ?);";
        try (PreparedStatement statement = ConfigDAO.connection.prepareStatement(query)) {
    
            // Set the values for the placeholders
            statement.setString(1, code);
            statement.setInt(2, year);
            statement.setInt(3, semestre);
            statement.setString(4, name);
            statement.setString(5, criterio);

            // Execute the query
            statement.executeUpdate();
            return true; 
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; 
    }

    public UC findByCode(String code) {
        String query = """
              SELECT year, semester, name, criterio
              FROM UC
              WHERE code = ?;
        """;

        UC uc = null;

        try (PreparedStatement statement = ConfigDAO.connection.prepareStatement(query)) {
            statement.setString(1, code);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int year = resultSet.getInt("year");
                int semester = resultSet.getInt("semester");
                String name = resultSet.getString("name");
                String criterio = resultSet.getString("criterio");

                uc = new UC(year, semester, name, criterio);
            }

        } catch (SQLException e) { e.printStackTrace(); }

        return uc;
    }

    public List<Turno> findAvailableShifts(String codigoUC) {
        String query = """
            SELECT idturno, sala, lotacao, tipo, horas, dia, quantidadedeinscritos, uc_code
            FROM  turno
            WHERE  uc_code = ? AND quantidadedeinscritos < lotacao
        """;

        List<Turno> turnosComVaga = new ArrayList<>();

        try (PreparedStatement statement = ConfigDAO.connection.prepareStatement(query)) {

            statement.setString(1, codigoUC);
            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()){
                String idturno = resultSet.getString("idturno");
                String receivedCodigoUC = resultSet.getString("uc_code");
                String sala = resultSet.getString("sala");
                int lotacao = resultSet.getInt("lotacao");
                LocalTime horas = resultSet.getTime("horas").toLocalTime();
                int dia = resultSet.getInt("dia");
                String tipo = resultSet.getString("tipo");
                int quantidadedeinscritos = resultSet.getInt("quantidadedeinscritos");

                Turno turno = new Turno(idturno,receivedCodigoUC,sala,lotacao,tipo,horas,dia,quantidadedeinscritos);
                turnosComVaga.add(turno);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return turnosComVaga;
    }
}
