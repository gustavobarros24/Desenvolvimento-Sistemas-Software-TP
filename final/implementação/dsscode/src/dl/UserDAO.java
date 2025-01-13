package dl;

import ln.models.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class UserDAO {
    private static UserDAO instance; // FIXME: Remover futuramente

    private final Map<String, User> users; // FIXME: Remover futuramente

    public UserDAO() {
        this.users = new HashMap<>();
    }

    public static UserDAO getInstance() {
        if (instance == null) {
            instance = new UserDAO();
        }
        return instance;
    }

    public Boolean save(String email, String password, String userType, String user_email, String diretor_email) {
        String query = "INSERT INTO User (email, password, userType, aluno_email, diretor_email) VALUES (?, ?, ?, ?, ?);";
        try (PreparedStatement statement = ConfigDAO.connection.prepareStatement(query)) {
    
            // Set the values for the placeholders
            statement.setString(1, email);
            statement.setString(2, password);
            statement.setString(3, userType);
            statement.setString(4, user_email);
            statement.setString(5, diretor_email);
    
            // Execute the query
            statement.executeUpdate();
            return true; 
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; 
    }

    public boolean save(User user) {
        String query = "INSERT INTO User (email, password, userType, aluno_email, diretor_email) VALUES (?, ?, ?, ?, ?);";
        try (PreparedStatement statement = ConfigDAO.connection.prepareStatement(query)) {
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getUserType().toString());

            if(user.getUserType() == User.UserType.STUDENT) {
                statement.setString(4, user.getEmail());
                statement.setString(5, null);
            } else {
                statement.setString(5, user.getEmail());
                statement.setString(4, null);
            }

            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            return false;
        }
    }

    public boolean save(String email, String password, User.UserType userType) {
        String query = "INSERT INTO User (email, password, userType, aluno_email, diretor_email) VALUES (?, ?, ?, ?, ?);";
        try (PreparedStatement statement = ConfigDAO.connection.prepareStatement(query)) {
            statement.setString(1, email);
            statement.setString(2, password);
            statement.setString(3, userType.toString());

            if(userType == User.UserType.STUDENT) {
                statement.setString(4, email);
                statement.setString(5, null);
            } else {
                statement.setString(5, email);
                statement.setString(4, null);
            }

            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            return false;
        }
    }

    public User findByEmail(String email) {
        String query = "SELECT email, password, userType FROM User WHERE email = ?;";

        User user = null;

        try (PreparedStatement statement = ConfigDAO.connection.prepareStatement(query)) {
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                user = new User(
                        resultSet.getString("email"),
                        resultSet.getString("password"),
                        null
                );
                String userType = resultSet.getString("userType");
                if (userType.equals(User.UserType.STUDENT.toString())) {
                    user.setUserType(User.UserType.STUDENT);
                } else {
                    user.setUserType(User.UserType.DIRECTOR);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    public String getUserPassword(String email){
        String query = "SELECT password FROM User WHERE email = ?";
        String userPassword = null;

        try (PreparedStatement statement = ConfigDAO.connection.prepareStatement(query)) {
            statement.setString(1,email);

            try(ResultSet rs = statement.executeQuery()){
                if(rs.next()){
                    userPassword = rs.getString("password");
                }
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return userPassword;
    }
}
