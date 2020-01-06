package JADevelopmentTeam;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class RoleScreenController {
    Stage stage = null;
    @FXML
    private Button adminLoginButton;
    @FXML
    private Button managerLoginButton;
    @FXML
    private Button workerLoginButton;
    // Holds this controller's Stage

    // Define the nodes from the Layout1.fxml file. This allows them to be referenced within the controller

    public void initData(Stage stage){
        this.stage = stage;
    }
    public RoleScreenController() {

    }

    /**
     * Show the stage that was loaded in the constructor
     */

    /**
     * The initialize() method allows you set setup your scene, adding actions, configuring nodes, etc.
     */
    @FXML
    private void adminLogin() {
        openLoginScreen(1);
        // Add an action for the "Open Layout2" button

    }
    @FXML
    private void managerLogin() {
        openLoginScreen(2);

        // Add an action for the "Open Layout2" button

    } @FXML
    private void workerLogin() {
        openLoginScreen(3);

        // Add an action for the "Open Layout2" button

    }
    /**
     * Performs the action of loading and showing Layout2
     */
    private void openLoginScreen(int userChosen) {

        // Create the second controller, which loads its own FXML file. We pass a reference to this controller
        // using the keyword [this]; that allows the second controller to access the methods contained in here.
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("login_screen.fxml"));
            stage.getScene().setRoot(fxmlLoader.load());
            LoginScreenController loginScreenController = fxmlLoader.getController();
            loginScreenController.initData(stage,userChosen);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Show the new stage/window
        //loginScreenController.showStage();

    }
    @FXML
    private void initialize() {
        adminLoginButton.setOnAction(event -> adminLogin());
        managerLoginButton.setOnAction(event -> managerLogin());
        workerLoginButton.setOnAction(event -> workerLogin());
    }
}
