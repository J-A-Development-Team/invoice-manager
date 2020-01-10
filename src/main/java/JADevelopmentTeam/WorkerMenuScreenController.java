package JADevelopmentTeam;

import JADevelopmentTeam.mysql.Database;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import javax.swing.text.html.ListView;
import java.io.IOException;
import java.util.ArrayList;

public class WorkerMenuScreenController  {
    public Label welcomeMessage;
    Stage stage = null;
    @FXML
    private Pane invoicePane,clientPane;
    @FXML
    private JFXButton invoiceMenuButton,clientMenuButton;
    @FXML
    private JFXButton addInvoiceButton;
    @FXML
    private JFXListView <Invoice>  invoicesListView = new JFXListView<>();
    ArrayList<Invoice> invoices = new ArrayList<>();
    Database database;
    User user = new User();
    public void initData(Stage stage, Database dataBase,User user) {
        this.database = dataBase;
        this.user = user;
        this.stage = stage;
        welcomeMessage.setText("Welcome \n"+user.name+"!");
    }


    public void handleMenuButtonAction(javafx.event.ActionEvent event) {
        if(event.getSource()==invoiceMenuButton){
            invoicePane.toFront();
        }else if(event.getSource() == clientMenuButton){
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
       // Invoice invoice = new Invoice();
       // invoicesListView.getItems().add(invoice);
    }
    @FXML
    private void initialize() {
        addInvoiceButton.setOnAction(event -> addStringToList());

    }
}
