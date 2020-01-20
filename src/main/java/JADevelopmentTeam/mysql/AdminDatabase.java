package JADevelopmentTeam.mysql;

import JADevelopmentTeam.Invoice;
import JADevelopmentTeam.User;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

public class AdminDatabase  {
    Connection connection;
    public AdminDatabase(Connection connection) {
        this.connection = connection;
    }


    public void addUser(User user) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("call  add_user(?,?,?)");
        preparedStatement.setString(1, user.getName());
        preparedStatement.setString(2, user.getPassword());
        preparedStatement.setString(3, user.getType().toString());
        preparedStatement.executeUpdate();
    }

    public void editUser(int userID, String newLogin, String newPassword) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("call  edit_user(?,?,?)");
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
    public static void save() {
        try {

            FileDialog dialog = new FileDialog((Frame) null, "Wybierz plik", FileDialog.SAVE);
            dialog.setFile("*.sql");
            dialog.setVisible(true);
            String filename = dialog.getFile();
            String dir = dialog.getDirectory();
            if (filename != null) {
                String savePath = dir +filename ;
                String executeCmd = "mysqldump.exe invoice_project --user=root -B --result-file "+savePath+" --password=ares";
                System.out.println(executeCmd);
                Process runtimeProcess = Runtime.getRuntime().exec(executeCmd);
                int processComplete = runtimeProcess.waitFor();
                if (processComplete == 0) {
                    System.out.println("Backup Complete");
                } else {
                    System.out.println("Backup Failure");
                }
            }
        } catch ( IOException | InterruptedException ex) {
            JOptionPane.showMessageDialog(null, "Error at Backuprestore" + ex.getMessage());
        }
    }
    public static void load() {
        try {
            FileDialog dialog = new FileDialog((Frame) null, "Wybierz plik", FileDialog.LOAD);
            dialog.setFile("*.sql");
            dialog.setVisible(true);
            String filename = dialog.getFile();
            String dir = dialog.getDirectory();
            if (filename != null) {
                String dbName = "invoice_project";
                String dbUser = "root";
                String dbPass = "ares";
                String restorePath = dir +filename ;
                String executeCmd = "Get-Content "+restorePath+" | mysql "+" -u" + dbUser+ " -p" + dbPass;
                System.out.println(executeCmd);
                Process runtimeProcess = Runtime.getRuntime().exec(executeCmd);
            }
        }catch(IOException | HeadlessException ex){
            JOptionPane.showMessageDialog(null, "Error at Restoredbfromsql" + ex.getMessage());
        }

    }
}
