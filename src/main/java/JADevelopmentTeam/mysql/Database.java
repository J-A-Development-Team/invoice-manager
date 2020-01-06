package JADevelopmentTeam.mysql;

import java.sql.*;

class Database {
    Connection connection;
    Statement statement;
    PreparedStatement preparedStatement;
Database(String user, String password){
    makeJDBCConnection(user, password);
}

    private void makeJDBCConnection(String user,String password){
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/invoice_project", user,password);
            statement = connection.createStatement();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
