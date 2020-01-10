package JADevelopmentTeam.mysql;

import java.sql.*;

class Database {
    Connection connection;
    Statement statement;
    PreparedStatement preparedStatement;

    Database(String user, String password) {
        makeJDBCConnection(user, password);
    }

    private void makeJDBCConnection(String user, String password) {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/invoice_project", user, password);
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected int authenticate(String login, String password, String userType) throws SQLException {
        preparedStatement = connection.prepareStatement("SELECT  authenticate(?,?,?)");
        preparedStatement.setString(1, login);
        preparedStatement.setString(2, password);
        preparedStatement.setString(3, userType);
        ResultSet rs = preparedStatement.executeQuery();
        rs.next();
        return rs.getInt(1);
    }
}
