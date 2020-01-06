package JADevelopmentTeam.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQLDatabase {
    Connection connection;
    Statement userStatement;
    Statement invoiceStatement;
    Statement itemStatement;

    public void connectToDatabase() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/invoiceManager", "administrator", "admin");
            userStatement = connection.createStatement();
            invoiceStatement = connection.createStatement();
            itemStatement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
