package JADevelopmentTeam;

import JADevelopmentTeam.mysql.AdminDatabase;
import JADevelopmentTeam.mysql.Database;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class AddUserScreenController {
    public JFXButton addUserButton;
    public PasswordField confirmPasswordTextField;
    public PasswordField passwordTextField;
    public TextField usernameTextField;
    public JFXListView<User> users;
    public JFXComboBox<User.Type> userTypeComboBox;
    AdminDatabase adminDatabase;
    Database database;
    User user = new User();
    private String username;
    private String password;
    private User.Type userType;

    public void initData(Database dataBase, User user, JFXListView<User> users) {
        this.database = dataBase;
        this.user = user;
        this.users = users;
        adminDatabase = new AdminDatabase(database.getConnection());
        userTypeComboBox.getItems().add(User.Type.admin);
        userTypeComboBox.getItems().add(User.Type.manager);
        userTypeComboBox.getItems().add(User.Type.worker);
    }

    private void getDataFromTextFields() {
        username = usernameTextField.getText();
        password = passwordTextField.getText();
        userType = userTypeComboBox.getValue();
    }

    private boolean checkIfAllFiled() {
        getDataFromTextFields();
        if (username.equals(""))
            return true;
        if (password.equals(""))
            return true;
        if (userType == null)
            return true;
        if (!password.equals(confirmPasswordTextField.getText()))
            return true;
        return false;
    }

    private void addNewUser() {
        getDataFromTextFields();
        User newUser = new User(userType, 0, username, password);
        try {
            adminDatabase.addUser(newUser);
            users.getItems().clear();
            users.getItems().addAll(adminDatabase.getAllUsers());
            addUserButton.getScene().getWindow().hide();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void initialize() {
        addUserButton.setOnAction(event -> addNewUser());
        usernameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            addUserButton.setDisable(checkIfAllFiled());
        });
        passwordTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            addUserButton.setDisable(checkIfAllFiled());
        });
        confirmPasswordTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            addUserButton.setDisable(checkIfAllFiled());
        });
        userTypeComboBox.itemsProperty().addListener((observable, oldValue, newValue) -> {
            addUserButton.setDisable(newValue == null);


        });
    }
}
