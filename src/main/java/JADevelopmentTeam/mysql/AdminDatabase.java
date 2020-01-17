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


                /*NOTE: Creating Database Constraints*/
                String dbName = "inovice_project";
                String dbUser = "invoice_admin";
                String dbPass = "admin_password";

                /*NOTE: Creating Path Constraints for folder saving*/
                /*NOTE: Here the backup folder is created for saving inside it*/
                String folderPath = dir + "\\backup";

                /*NOTE: Creating Folder if it does not exist*/
                File f1 = new File(folderPath);
                f1.mkdir();

                /*NOTE: Creating Path Constraints for backup saving*/
                /*NOTE: Here the backup is saved in a folder called backup with the name backup.sql*/
                String savePath = "\"" + dir + "\\backup\\" + "backup.sql\"";

                /*NOTE: Used to create a cmd command*/
                String executeCmd = "mysqldump -u" + dbUser + " -p" + dbPass + " --database " + dbName + " -r " + savePath;

                /*NOTE: Executing the command here*/
                Process runtimeProcess = Runtime.getRuntime().exec(executeCmd);
                int processComplete = runtimeProcess.waitFor();

                /*NOTE: processComplete=0 if correctly executed, will contain other values if not*/
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


                String dbName = "inovice_project";
                String dbUser = "invoice_admin";
                String dbPass = "admin_password";

                /*NOTE: Creating Path Constraints for restoring*/
                String restorePath = dir + "\\backup" + "\\" +filename;

                /*NOTE: Used to create a cmd command*/
                /*NOTE: Do not create a single large string, this will cause buffer locking, use string array*/
                String[] executeCmd = new String[]{"mysql", dbName, "-u" + dbUser, "-p" + dbPass, "-e", " source " + restorePath};

                /*NOTE: processComplete=0 if correctly executed, will contain other values if not*/
                Process runtimeProcess = Runtime.getRuntime().exec(executeCmd);
                int processComplete = runtimeProcess.waitFor();

                /*NOTE: processComplete=0 if correctly executed, will contain other values if not*/
                if (processComplete == 0) {
                    JOptionPane.showMessageDialog(null, "Successfully restored from SQL : " + filename);
                } else {
                    JOptionPane.showMessageDialog(null, "Error at restoring");
                }


            }
        }catch(IOException | InterruptedException | HeadlessException ex){
            JOptionPane.showMessageDialog(null, "Error at Restoredbfromsql" + ex.getMessage());
        }

    }
}
