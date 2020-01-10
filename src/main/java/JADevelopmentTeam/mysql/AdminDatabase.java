package JADevelopmentTeam.mysql;

import java.sql.Connection;
import java.sql.SQLException;

public class AdminDatabase extends Database {
    public AdminDatabase(Connection connection) {
        super(connection);
    }

    public AdminDatabase(String user, String password) throws SQLException {
        super(user, password);
    }

    public void addUser(String login, String password, String userType) throws SQLException {
        preparedStatement = connection.prepareStatement("call  add_user(?,?,?)");
        preparedStatement.setString(1, login);
        preparedStatement.setString(2, password);
        preparedStatement.setString(3, userType);
        preparedStatement.executeUpdate();
    }

    public void editUser(int userID, String newLogin, String newPassword) throws SQLException {
        preparedStatement = connection.prepareStatement("call  edit_user(?,?,?)");
        preparedStatement.setInt(1, userID);
        preparedStatement.setString(2, newLogin);
        preparedStatement.setString(3, newPassword);
        preparedStatement.executeUpdate();
    }
}
