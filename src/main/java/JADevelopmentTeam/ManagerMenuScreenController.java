package JADevelopmentTeam;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import javax.swing.text.html.ListView;
import java.io.IOException;

public class ManagerMenuScreenController  {
    Stage stage = null;
    @FXML
    private Pane invoicePane,clientPane;
    @FXML
    private JFXButton invoiceMenuButton,clientMenuButton;
    @FXML
    private JFXButton addInvoiceButton;
    @FXML
    private JFXListView <String>  invoicesListView = new JFXListView<>();
    public void initData(Stage stage) {
        this.stage = stage;
    }
    public void handleMenuButtonAction(javafx.event.ActionEvent event) {
        if(event.getSource()==invoiceMenuButton){
            invoicePane.toFront();
        }else if(event.getSource() == addInvoiceButton){
            clientPane.toFront();
        }else{
            logout();
        }
    }
    private void logout(){
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("role_screen.fxml"));
        try {
            stage.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        RoleScreenController roleScreenController = fxmlLoader.getController();
        roleScreenController.initData(stage);
    }
    private void addStringToList(){
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(App.class.getResource("add_invoice_screen.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(scene);
        stage.show();

        invoicesListView.getItems().add("Masło");
    }
    @FXML
    private void initialize() {
        addInvoiceButton.setOnAction(event -> addStringToList());

        ObservableList<String> seasonList = FXCollections.<String>observableArrayList("Spring", "Summer", "Fall", "Winter");
        invoicesListView.getItems().addAll(seasonList);
    }
}
