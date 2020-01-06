package JADevelopmentTeam;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginScreenController {


    public AnchorPane parentPane;
    private Stage stage = null;
    private int userType;

    // Will hold a reference to the first controller, allowing us to access the methods found there.
    @FXML
    private JFXButton loginButton;

    // Add references to the controls in Layout2.fxml
    public void initData(Stage stage, int userType) {
        this.stage = stage;
        this.userType = userType;
        switch (userType) {
            case 1:
                parentPane.setStyle("-fx-background-color:  #c9160c");
                break;
            case 2:
                parentPane.setStyle("-fx-background-color:  #3ed111");
                break;
            default:
                parentPane.setStyle("-fx-background-color:  #0787f0");
                break;

        }
    }

    public void login() {
        try {
            FXMLLoader fxmlLoader;
            switch (userType) {
                case 1:
                    fxmlLoader = new FXMLLoader(App.class.getResource("admin_menu_screen.fxml"));

                    break;
                case 2:
                    fxmlLoader = new FXMLLoader(App.class.getResource("manager_menu_screen.fxml"));
                    break;
                default:
                    fxmlLoader = new FXMLLoader(App.class.getResource("worker_menu_screen.fxml"));
                    break;

            }
            stage.getScene().setRoot(fxmlLoader.load());
            switch (userType) {
                case 1:
                    AdminMenuScreenController adminMenuScreenController = fxmlLoader.getController();
                    adminMenuScreenController.initData(stage);
                    break;
                case 2:
                    ManagerMenuScreenController managerMenuScreenController = fxmlLoader.getController();
                    managerMenuScreenController.initData(stage);
                    break;
                default:
                    WorkerMenuScreenController workerMenuScreenController = fxmlLoader.getController();
                    workerMenuScreenController.initData(stage);
                    break;

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void initialize() {
        loginButton.setOnAction(event -> login());
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