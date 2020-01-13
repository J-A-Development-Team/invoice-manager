package JADevelopmentTeam.UI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("role_screen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 600);
        stage.setScene(scene);
        RoleScreenController roleScreenController = fxmlLoader.getController();
        roleScreenController.initData(stage);
        stage.setTitle("Invoice App");
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }

}