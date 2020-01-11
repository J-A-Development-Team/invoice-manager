package JADevelopmentTeam;

import JADevelopmentTeam.mysql.ClientDatabase;
import JADevelopmentTeam.mysql.Database;
import JADevelopmentTeam.mysql.InvoiceDatabase;
import JADevelopmentTeam.mysql.ItemDatabase;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class MenuScreenController {
    public JFXButton itemMenuButton;
    public Label welcomeMessage;
    public JFXButton logoutButton;
    public AnchorPane menuSidePane;
    public JFXButton addClientButton;
    public Pane invoicePane,clientPane;
    public JFXButton invoiceMenuButton,clientMenuButton;
    public JFXButton addInvoiceButton;
    public AnchorPane itemPane;
    public JFXButton addItemButton;
    public JFXListView <Client> clientsListView = new JFXListView<>();
    public JFXListView <Item> itemsListView = new JFXListView<>();
    public JFXListView <Invoice>  invoicesListView = new JFXListView<>();
    Database database;
    InvoiceDatabase invoiceDatabase;
    ClientDatabase clientDatabase;
    ItemDatabase itemsDatabase;
    ObservableList<Invoice> invoices = FXCollections.<Invoice>observableArrayList();
    ObservableList<Client> clients = FXCollections.<Client>observableArrayList();
    ObservableList<Item> items = FXCollections.<Item>observableArrayList();
    User user = new User();
    Stage stage = null;

    public void initData(Stage stage, Database dataBase,User user) {
        this.database = dataBase;
        this.user = user;
        this.stage = stage;
        invoiceDatabase = new InvoiceDatabase(database.getConnection());
        clientDatabase = new ClientDatabase(database.getConnection());
        itemsDatabase = new ItemDatabase(database.getConnection());
        initializeWithData();
    }
    private void configureForUserType(){
        switch (user.type) {
            case admin:
                menuSidePane.setStyle("-fx-background-color:  #c9160c");
                break;
            case manager:
                menuSidePane.setStyle("-fx-background-color:  #3ed111");
                break;
            case worker:
                menuSidePane.setStyle("-fx-background-color:  #0787f0");
                addItemButton.setVisible(false);
                break;
        }
    }
    private void initializeWithData(){
        welcomeMessage.setText("Welcome \n"+user.name+"!");
        configureForUserType();
        try {
            invoices.addAll(invoiceDatabase.getAllInvoices());
            invoicesListView.getItems().addAll(invoices);
            clients.addAll(clientDatabase.getAllClients());
            clientsListView.getItems().addAll(clients);
            items.addAll(itemsDatabase.getAllItems());
            itemsListView.getItems().addAll(items);
            invoicePane.toFront();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void handleMenuButtonAction(javafx.event.ActionEvent event) {
        if(event.getSource()==invoiceMenuButton){
            invoicePane.toFront();
        }else if(event.getSource() == clientMenuButton) {
            clientPane.toFront();
        }else if(event.getSource() == itemMenuButton){
            itemPane.toFront();
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
    private void addInvoice(){
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(App.class.getResource("add_invoice_screen.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(loader.load());
            AddInvoiceScreenController addInvoiceScreenController = loader.getController();
            addInvoiceScreenController.initData(database,user,invoicesListView);
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(scene);
        stage.show();
    }
    private void addClient() {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(App.class.getResource("add_client_screen.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(loader.load());
            AddClientScreenController addClientScreenController = loader.getController();
            addClientScreenController.initData(database,user,clientsListView);
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(scene);
        stage.show();
    }
    private void addItem(){
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(App.class.getResource("add_item_screen.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(loader.load());
            AddItemScreenController addItemScreenController = loader.getController();
            addItemScreenController.initData(database,user,itemsListView);
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private void initialize() {
        addInvoiceButton.setOnAction(event -> addInvoice());
        addClientButton.setOnAction(event -> addClient());
        addItemButton.setOnAction(event -> addItem());
    }


}
