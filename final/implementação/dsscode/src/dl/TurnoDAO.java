package dl;

import java.sql.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ln.models.Turno;

public class TurnoDAO {
    private static TurnoDAO instance; // FIXME: Remover futuramente

    private final Map<Integer, Turno> turnos; // FIXME: Remover futuramente

    public TurnoDAO() {
        this.turnos = new HashMap<>();
    }

    public static TurnoDAO getInstance() {
        if (instance == null) {
            instance = new TurnoDAO();
        }
        return instance;
    }

    public Turno findByID(String idTurno) {
        String query = "SELECT idturno, sala, lotacao, tipo, horas, dia, quantidadedeinscritos, uc_code FROM Turno WHERE idturno = ?";

        Turno turno = null;

        try (PreparedStatement statement = ConfigDAO.connection.prepareStatement(query)) {

            statement.setString(1, idTurno);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String idturno = resultSet.getString("idturno");
                String codigoUC = resultSet.getString("uc_code");
                String sala = resultSet.getString("sala");
                int lotacao = resultSet.getInt("lotacao");
                LocalTime horas = resultSet.getTime("horas").toLocalTime();
                int dia = resultSet.getInt("dia");
                String tipo = resultSet.getString("tipo");
                int quantidadedeinscritos = resultSet.getInt("quantidadedeinscritos");

                turno = new Turno(idturno,codigoUC,sala,lotacao,tipo,horas,dia,quantidadedeinscritos);
            }

        } 
        catch (SQLException e) { 
            e.printStackTrace(); 
        }

        return turno;
    }

    public Boolean save(String idTurno, String sala, int lotacao, String tipo, Date horas, int dia, int quantidadeInscritos, String codigoUC) {
        String query = "INSERT INTO Turno (idTurno, sala, lotacao, tipo, horas, dia, quantidadedeinscritos, uc_code) VALUES (?, ?, ?, ?, ?, ?,?,?);";
        try (PreparedStatement statement = ConfigDAO.connection.prepareStatement(query)) {
    
            // Set the values for the placeholders
            statement.setString(1, idTurno);
            statement.setString(2, sala);
            statement.setInt(3, lotacao);
            statement.setString(4, tipo);
            statement.setTime(5, new Time(horas.getTime()));
            statement.setInt(6, dia);
            statement.setInt(7, quantidadeInscritos);
            statement.setString(8, codigoUC);
    
            // Execute the query
            statement.executeUpdate();
            return true; 
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; 
    }

    public List<Turno> getTodosOsTurnos(){
        List<Turno> turnos = new ArrayList<>();
        String sql = """
                SELECT idturno, sala, lotacao, tipo, horas, dia, quantidadedeinscritos, uc_code FROM Turno;
                """;
        try (PreparedStatement statement = ConfigDAO.connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()){
                String idturno = resultSet.getString("idturno");
                String codigoUC = resultSet.getString("uc_code");
                String sala = resultSet.getString("sala");
                int lotacao = resultSet.getInt("lotacao");
                LocalTime horas = resultSet.getTime("horas").toLocalTime();
                int dia = resultSet.getInt("dia");
                String tipo = resultSet.getString("tipo");
                int quantidadedeinscritos = resultSet.getInt("quantidadedeinscritos");
                
                Turno turno = new Turno(idturno,codigoUC,sala,lotacao,tipo,horas,dia,quantidadedeinscritos);
                turnos.add(turno);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return turnos;
    }


    public void libertartodasasvagas(){
        String sql = "UPDATE `horariogen`.`Turno` SET `quantidadedeinscritos` = 0";
        try(PreparedStatement statement = ConfigDAO.connection.prepareStatement(sql)){
            statement.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
        }

    }

    public void updateQuantidadeInscritos(String idTurno, int quantidadeDeInscritos){
        String sql = "UPDATE turno SET quantidadeDeInscritos = ? WHERE idturno = ?";
        try(PreparedStatement statement = ConfigDAO.connection.prepareStatement(sql)){
            statement.setInt(1,quantidadeDeInscritos);
            statement.setString(2, idTurno);
            statement.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
}
