package bankingmanagement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class bankDAO {
    private static final String URL = "jdbc:mysql://localhost:3306/bankingapp";
    private static final String USER = "root";
    private static final String PASSWORD = "Sound@1311";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new SQLException("JDBC Driver not found");
        }
    }
}
