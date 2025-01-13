package dl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ln.models.Aluno;
import ln.models.Turno;
import ln.models.UC;

public class AlunoDAO {
    private static AlunoDAO instance; // FIXME: Remover futuramente

    public Map<String, Aluno> students; // FIXME: Remover futuramente

    public AlunoDAO() {
        students = new HashMap<>();
    }

    public static AlunoDAO getInstance() {
        if (instance == null) {
            instance = new AlunoDAO();
        }
        return instance;
    }

    public void save(String email, String estatuto, int matricula, String nome) {
        String query = "INSERT INTO Aluno (email, estatuto, matricula, nome) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = ConfigDAO.connection.prepareStatement(query)) {
    
            // Set the values for the placeholders
            statement.setString(1, email);
            statement.setString(2, estatuto);
            statement.setInt(3, matricula);
            statement.setString(4, nome);
    
            // Execute the query
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
    }

    public Aluno findByEmail(String email) {
        String query = """
              SELECT email, estatuto, matricula, nome
              FROM aluno
              WHERE email = ?      
        """;

        Aluno aluno = null;

        try (PreparedStatement statement = ConfigDAO.connection.prepareStatement(query)) {

            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String retrievedEmail = resultSet.getString("email");
                String estatuto = resultSet.getString("estatuto");
                int matricula = resultSet.getInt("matricula");
                String nome = resultSet.getString("nome");
                List<Turno> horario = getTurnosInscrito(email);
                aluno = new Aluno(retrievedEmail, "", estatuto, matricula, nome,horario);
            }

        } catch (SQLException e) { 
            e.printStackTrace(); 
        }

        return aluno;
    }

    public boolean inscreverEmUC(String email, String codigoUC) {
        String query = "INSERT INTO Ucsfrequentadas (aluno_email, uc_code) VALUES (?, ?)";

        try (PreparedStatement statement = ConfigDAO.connection.prepareStatement(query)) {
            statement.setString(1, email);
            statement.setString(2, codigoUC);

            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            return false;
        }

        return true;
    }

    public boolean inscreverEmTurno(String email, String idTurno) {
        String queryInscrever = """
        INSERT INTO horario (aluno_email, turno_idturno)
        VALUES (?, ?)
    """;

        String queryIncrementar = """
        UPDATE turno
        SET quantidadedeinscritos = quantidadedeinscritos + 1
        WHERE idturno = ?
    """;

        try {
            ConfigDAO.connection.setAutoCommit(false);

            try (PreparedStatement statement = ConfigDAO.connection.prepareStatement(queryInscrever)) {
                statement.setString(1, email);
                statement.setString(2, idTurno);

                int rowsAffected = statement.executeUpdate();
                if (rowsAffected == 0) {
                    ConfigDAO.connection.rollback();
                    return false;
                }
            }

            try (PreparedStatement statement = ConfigDAO.connection.prepareStatement(queryIncrementar)) {
                statement.setString(1, idTurno);

                int rowsAffected = statement.executeUpdate();
                if (rowsAffected == 0) {
                    ConfigDAO.connection.rollback();
                    return false;
                }
            }

            // Commit the transaction if both queries succeed
            ConfigDAO.connection.commit();
            return true;

        } catch (SQLException e) {
            //e.printStackTrace();
            try { ConfigDAO.connection.rollback(); }
            catch (SQLException rollbackEx) { rollbackEx.printStackTrace(); }
            return false;

        } finally {
            try { ConfigDAO.connection.setAutoCommit(true); }
            catch (SQLException e) { e.printStackTrace(); }
        }
    }


    public List<Turno> getTurnosInscrito(String email) {
        String query = """
            SELECT 
                turno.idturno, 
                turno.sala, 
                turno.lotacao, 
                turno.tipo, 
                turno.horas, 
                turno.quantidadedeinscritos, 
                turno.uc_code
            FROM 
                horario
            JOIN 
                turno ON horario.turno_idturno = turno.idturno
            WHERE 
                horario.aluno_email = ?
        """;

        List<Turno> horarios = new ArrayList<>();

        try (PreparedStatement statement = ConfigDAO.connection.prepareStatement(query)) {

            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String idTurno = resultSet.getString("idturno");
                String sala = resultSet.getString("sala");
                int lotacao = Integer.parseInt(resultSet.getString("lotacao")); // Assuming `lotacao` is stored as `VARCHAR`
                String tipo = resultSet.getString("tipo");
                LocalTime horas = LocalTime.parse(resultSet.getString("horas"));
                int quantidadeDeInscritos = resultSet.getInt("quantidadedeinscritos");
                String codigoUC = resultSet.getString("uc_code");

                // Create a Turno object for each result
                Turno turno = new Turno(idTurno, codigoUC, sala, lotacao, tipo, horas, quantidadeDeInscritos);
                horarios.add(turno);
            }

        } catch (SQLException e) { e.printStackTrace(); }

        return horarios;
    }

    public List<Turno> getTurnosInscrito(String email, String codigoUC) {
        String query = """
        SELECT 
            turno.idturno, 
            turno.sala, 
            turno.lotacao, 
            turno.tipo, 
            turno.horas, 
            turno.quantidadedeinscritos, 
            turno.uc_code
        FROM 
            horario
        JOIN 
            turno ON horario.turno_idturno = turno.idturno
        WHERE 
            horario.aluno_email = ? AND turno.uc_code = ?
    """;

        List<Turno> horarios = new ArrayList<>();

        try (PreparedStatement statement = ConfigDAO.connection.prepareStatement(query)) {

            statement.setString(1, email);
            statement.setString(2, codigoUC);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String idTurno = resultSet.getString("idturno");
                String sala = resultSet.getString("sala");
                int lotacao = Integer.parseInt(resultSet.getString("lotacao")); // Assuming `lotacao` is stored as `VARCHAR`
                String tipo = resultSet.getString("tipo");
                LocalTime horas = LocalTime.parse(resultSet.getString("horas"));
                int quantidadeDeInscritos = resultSet.getInt("quantidadedeinscritos");

                // Create a Turno object for each result
                Turno turno = new Turno(idTurno, codigoUC, sala, lotacao, tipo, horas, quantidadeDeInscritos);
                horarios.add(turno);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return horarios;
    }

    public boolean desinscreverDeTurno(String email, String idTurno) {
        String queryDesinscrever = """
            DELETE FROM horario 
            WHERE aluno_email = ? AND turno_idturno = ?
        """;
        String queryDecrementar = """
            UPDATE turno
            SET quantidadedeinscritos = quantidadedeinscritos - 1
            WHERE idturno = ? AND quantidadedeinscritos > 0
        """;

        try {
            // Disable auto-commit to start a transaction
            ConfigDAO.connection.setAutoCommit(false);

            // First query: Remove the association from the `horario` table
            try (PreparedStatement statement = ConfigDAO.connection.prepareStatement(queryDesinscrever)) {
                statement.setString(1, email);
                statement.setString(2, idTurno);

                int rowsAffected = statement.executeUpdate();
                if (rowsAffected == 0) {
                    // No rows were deleted, rollback the transaction and return false
                    ConfigDAO.connection.rollback();
                    return false;
                }
            }

            // Second query: Decrement the inscritos count in the `turno` table
            try (PreparedStatement statement = ConfigDAO.connection.prepareStatement(queryDecrementar)) {
                statement.setString(1, idTurno);

                int rowsAffected = statement.executeUpdate();
                if (rowsAffected == 0) {
                    // No rows were updated, rollback the transaction and return false
                    ConfigDAO.connection.rollback();
                    return false;
                }
            }

            // Commit the transaction if both queries succeed
            ConfigDAO.connection.commit();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            try { ConfigDAO.connection.rollback(); }
            catch (SQLException rollbackEx) { rollbackEx.printStackTrace(); }
            return false;
        }  finally {
            try { ConfigDAO.connection.setAutoCommit(true); }
            catch (SQLException e) { e.printStackTrace(); }
        }
    }

    public boolean hasSchedule(String email) {
        String query = """
                SELECT 1
                FROM Horario
                WHERE aluno_email = ?
                LIMIT 1;
        """;
        try (PreparedStatement statement = ConfigDAO.connection.prepareStatement(query)) {
            statement.setString(1, email);

            ResultSet resultSet = statement.executeQuery();

            return resultSet.next();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean findSchedule(String email) {
        String sql = """
                SELECT 
                    turno.idturno AS ID_Turno,
                    turno.horas AS Horas
                FROM
                    aluno
                JOIN
                    horario ON aluno.email = horario.aluno_email
                JOIN
                    turno ON horario.turno_idturno = turno.idturno
                WHERE
                    aluno.email = ?
                """;

        try(PreparedStatement statement = ConfigDAO.connection.prepareStatement(sql)){
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();

            System.out.println("Turnos do aluno:");

            while(resultSet.next()){
                String idTurno = resultSet.getString("ID_Turno");
                String horas = resultSet.getString("Horas");
                System.out.println("Turno: " + idTurno + ", Horas: "+ horas);
            }         
            
            return true;
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public Map<String,UC> getUCsFrequentadas(String alunoEmail) {
        Map<String, UC> ucsFrequentadas = new HashMap<>();

        String query = """
                SELECT 
                    uc.code, uc.year, uc.semester, uc.name, uc.criterio
                FROM
                    ucsfrequentadas
                JOIN
                    uc ON ucsfrequentadas.uc_code = uc.code
                WHERE
                    ucsfrequentadas.aluno_email = ?
                """;


        try (PreparedStatement statement = ConfigDAO.connection.prepareStatement(query)) {

            statement.setString(1, alunoEmail);
            try(ResultSet resultSet = statement.executeQuery()){
                while(resultSet.next()){
                    String code = resultSet.getString("code");
                    int year = resultSet.getInt("year");
                    int semester = resultSet.getInt("semester");
                    String name = resultSet.getString("name");
                    String criterio = resultSet.getString("criterio");

                    ucsFrequentadas.put(code,new UC(year,semester,code,name,criterio));
                }
            }
        }
        
        catch (SQLException e) { 
            e.printStackTrace();
        }

        return ucsFrequentadas;
    }

    public List<Aluno> getTodosOsAlunos(){
        List<Aluno> alunos = new ArrayList<>();
        String sql = """
                SELECT email, estatuto, matricula, nome
                FROM aluno
                """;

        try(PreparedStatement statement = ConfigDAO.connection.prepareStatement(sql)){
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()){
                String email = resultSet.getString("email");
                String estatuto = resultSet.getString("estatuto");
                int matricula = resultSet.getInt("matricula");
                String nome = resultSet.getString("nome");
                Map<String,UC> ucsinscritas = getUCsFrequentadas(email);
                Aluno aluno = new Aluno(email, email, estatuto, matricula, nome, ucsinscritas);
                alunos.add(aluno);
            }

        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return alunos;
    }


    public void registarHorario(String email, String idTurno){
        String sql = "INSERT INTO horario (aluno_email, turno_idturno) VALUES (?, ?)";

        try(PreparedStatement statement = ConfigDAO.connection.prepareStatement(sql)){
            statement.setString(1,email);
            statement.setString(2, idTurno);
            statement.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void apagarTodosOsHorarios(){
        String sql = "DELETE FROM Horario";
        try(PreparedStatement statement = ConfigDAO.connection.prepareStatement(sql)) {
            statement.executeUpdate();
        } 
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
