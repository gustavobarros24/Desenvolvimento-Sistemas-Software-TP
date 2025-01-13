package dl;

import ln.models.Diretor;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class DiretorDAO {
    private static DiretorDAO instance; // FIXME: Remover futuramente

    private final Map<String, Diretor> directors; // FIXME: Remover futuramente

    public DiretorDAO() {
        directors = new HashMap<>();
    }

    public static DiretorDAO getInstance() {
        if (instance == null) {
            instance = new DiretorDAO();
        }
        return instance;
    }

    public Boolean save(String email, String name) {
         String query = "INSERT INTO Diretor (email, name) VALUES (?, ?);";
        try (PreparedStatement statement = ConfigDAO.connection.prepareStatement(query)) {
    
            // Set the values for the placeholders
            statement.setString(1, email);
            statement.setString(2, name);
            // Execute the query
            statement.executeUpdate();
            return true; 
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; 
    }

    public Boolean save(Diretor diretor) {
        String query = "INSERT INTO Diretor (email, name) VALUES (?, ?);";
        try (PreparedStatement statement = ConfigDAO.connection.prepareStatement(query)) {
            statement.setString(1, diretor.getEmail());
            statement.setString(2, diretor.getName());

            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            return false;
        }
    }
}
