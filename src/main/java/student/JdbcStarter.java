package student;

import java.sql.Connection;
import java.sql.SQLException;

public class JdbcStarter {
    public static void main(String[] args) throws SQLException {
        try (Connection connection = ConnectionManager.open()){
            System.out.println(connection.getTransactionIsolation());
        }
    }
}
