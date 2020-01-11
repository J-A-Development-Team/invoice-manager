package JADevelopmentTeam.mysql;

import JADevelopmentTeam.Invoice;
import JADevelopmentTeam.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

public class AdminDatabase extends Database {
    public AdminDatabase(Connection connection) {
        super(connection);
    }

    public AdminDatabase(String user, String password) throws SQLException {
        super(user, password);
    }

    public void addUser(User user) throws SQLException {
        preparedStatement = connection.prepareStatement("call  add_user(?,?,?)");
        preparedStatement.setString(1, user.getName());
        preparedStatement.setString(2, user.getPassword());
        preparedStatement.setString(3, user.getType().toString());
        preparedStatement.executeUpdate();
    }

    public void editUser(int userID, String newLogin, String newPassword) throws SQLException {
        preparedStatement = connection.prepareStatement("call  edit_user(?,?,?)");
        preparedStatement.setInt(1, userID);
        preparedStatement.setString(2, newLogin);
        preparedStatement.setString(3, newPassword);
        preparedStatement.executeUpdate();
    }
    public ArrayList<User> getAllUsers() throws SQLException {
        ArrayList<User> users = new ArrayList<>();
        ResultSet rs = connection.prepareStatement("call get_all_users()").executeQuery();
        while (rs.next()) {
            users.add(resultToUser(rs));
        }
        Collections.reverse(users);
        return users;
    }
    private User resultToUser(ResultSet rs) throws SQLException {
      return  new User(User.stringToType(rs.getString("type")),rs.getInt("id"),rs.getString("name"),rs.getString("password"));

    }
}
