package JADevelopmentTeam;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginScreenController {


    private Stage thisStage;

    // Will hold a reference to the first controller, allowing us to access the methods found there.
    @FXML
    private JFXButton loginButton;
    // Add references to the controls in Layout2.fxml


    public LoginScreenController() {
        // We received the first controller, now let's make it usable throughout this controller.
        // Create the new stage
    }

    /**
     * Show the stage that was loaded in the constructor
     */
    public void showStage() {
        thisStage.showAndWait();
    }
    public void login(){
        try {
            App.setRoot("worker_menu_screen");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void initialize() {
        loginButton.setOnAction(event -> login());
    }

}