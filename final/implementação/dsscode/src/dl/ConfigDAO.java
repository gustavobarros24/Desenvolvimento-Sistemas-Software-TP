package dl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConfigDAO {
    public static final Connection connection = connect();

    public static final String username = "root";
    public static final String password = "1234";
    static final String url = "jdbc:mysql://localhost:3306/horariogen";

    private static Connection connect() {
        try {
            Connection conn = DriverManager.getConnection(url, username, password);
            System.out.println("Conexão com a Base de Dados estabelecida!");
            return conn;
        } catch (SQLException e) {
            System.out.println("Erro ao conectar à base de dados: " + e.getMessage());
            System.exit(1);
            throw new RuntimeException(e);
        }
    }
}
