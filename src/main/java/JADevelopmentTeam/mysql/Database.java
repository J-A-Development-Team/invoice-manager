package JADevelopmentTeam.mysql;

import java.sql.*;

class Database {
    Connection connection;
    PreparedStatement preparedStatement;

    public Database(String user, String password) throws SQLException {
        makeJDBCConnection(user, password);
    }

    public Database(Connection connection){
        this.connection = connection;
    }

    private void makeJDBCConnection(String user, String password) throws SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/invoice_project", user, password);
    }

    public Connection getConnection() {
        return connection;
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
