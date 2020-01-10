package JADevelopmentTeam;

import JADevelopmentTeam.mysql.Database;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class LoginScreenController {


    public AnchorPane parentPane;
    public TextField loginTextField;
    public PasswordField passwordTextField;
    public Label wrongPasswordLabel;
    String login, password;
    Database database;
    User user = new User();
    private Stage stage = null;
    // Will hold a reference to the first controller, allowing us to access the methods found there.
    @FXML
    private JFXButton loginButton;

    // Add references to the controls in Layout2.fxml
    public void initData(Stage stage, User user) {
        this.stage = stage;
        this.user = user;
        try {
            switch (user.type) {
                case admin:
                    parentPane.setStyle("-fx-background-color:  #c9160c");
                    database = new Database("invoice_admin", "admin_password");
                    break;
                case manager:
                    parentPane.setStyle("-fx-background-color:  #3ed111");
                    database = new Database("invoice_manager", "manager_password");
                    break;
                case worker:
                    parentPane.setStyle("-fx-background-color:  #0787f0");
                    database = new Database("invoice_worker", "worker_password");
                    break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String getUserTypeAsString() {
        switch (user.type) {
            case admin:
                return "admin";
            case manager:
                return "manager";
            default:
                return "worker";
        }
    }

    private void handleWrongCredentials() {
        notifyAboutWrongCredentials();
        clearInputFields();
    }

    private void clearInputFields() {
        loginTextField.setText("");
        passwordTextField.setText("");
    }

    private void notifyAboutWrongCredentials() {
        wrongPasswordLabel.setVisible(true);
    }

    private void accumulateCredentials() {
        login = loginTextField.getText();
        password = passwordTextField.getText();
    }

    public void login() throws SQLException {
        accumulateCredentials();
        int loginResult = database.authenticate(login, password, getUserTypeAsString());
        if (loginResult == -1) {
            handleWrongCredentials();
        } else {
            user.id = loginResult;
            user.name = login;
            try {
                FXMLLoader fxmlLoader;
                switch (user.type) {
                    case admin:
                        fxmlLoader = new FXMLLoader(App.class.getResource("admin_menu_screen.fxml"));

                        break;
                    case manager:
                        fxmlLoader = new FXMLLoader(App.class.getResource("manager_menu_screen.fxml"));
                        break;
                    default:
                        fxmlLoader = new FXMLLoader(App.class.getResource("worker_menu_screen.fxml"));
                        break;

                }
                stage.getScene().setRoot(fxmlLoader.load());
                switch (user.type) {
                    case admin:
                        AdminMenuScreenController adminMenuScreenController = fxmlLoader.getController();
                        adminMenuScreenController.initData(stage, database, user);
                        break;
                    case manager:
                        ManagerMenuScreenController managerMenuScreenController = fxmlLoader.getController();
                        managerMenuScreenController.initData(stage, database, user);
                        break;
                    default:
                        WorkerMenuScreenController workerMenuScreenController = fxmlLoader.getController();
                        workerMenuScreenController.initData(stage, database, user);
                        break;

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @FXML
    private void initialize() {
        loginButton.setOnAction(event -> {
            try {
                login();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public void back(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("role_screen.fxml"));
        try {
            stage.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        RoleScreenController roleScreenController = fxmlLoader.getController();
        roleScreenController.initData(stage);
    }

}