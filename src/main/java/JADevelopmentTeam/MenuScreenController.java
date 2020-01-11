package JADevelopmentTeam;

import JADevelopmentTeam.mysql.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXToggleButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public class MenuScreenController {
    public JFXButton itemMenuButton;
    public Label welcomeMessage;
    public JFXButton logoutButton;
    public AnchorPane menuSidePane;
    public JFXButton addClientButton;
    public Pane invoicePane, clientPane;
    public JFXButton invoiceMenuButton, clientMenuButton;
    public JFXButton addInvoiceButton;
    public AnchorPane itemPane;
    public JFXButton addItemButton;
    public JFXListView<Client> clientsListView = new JFXListView<>();
    public JFXListView<Item> itemsListView = new JFXListView<>();
    public JFXListView<Invoice> invoicesListView = new JFXListView<>();
    public JFXListView <User> usersListView = new JFXListView<>();
    public JFXButton editAvailableAmountButton;
    public JFXButton editItemCostButton;
    public JFXToggleButton showOnlyAvailableItemsToggleButton;
    public JFXButton deleteItemButton;
    public JFXButton deleteInvoiceButton;
    public JFXButton addUserButton;
    public JFXButton editUserButton;
    public AnchorPane usersPane;
    public JFXButton userMenuButton;
    Database database;
    InvoiceDatabase invoiceDatabase;
    ClientDatabase clientDatabase;
    ItemDatabase itemsDatabase;
    AdminDatabase adminDatabase;
    ObservableList<Invoice> invoices = FXCollections.<Invoice>observableArrayList();
    ObservableList<Client> clients = FXCollections.<Client>observableArrayList();
    ObservableList<Item> items = FXCollections.<Item>observableArrayList();
    ObservableList<User> users = FXCollections.<User>observableArrayList();

    User user = new User();
    Stage stage = null;

    public void initData(Stage stage, Database dataBase, User user) {
        this.database = dataBase;
        this.user = user;
        this.stage = stage;
        invoiceDatabase = new InvoiceDatabase(database.getConnection());
        clientDatabase = new ClientDatabase(database.getConnection());
        itemsDatabase = new ItemDatabase(database.getConnection());
        adminDatabase = new AdminDatabase(database.getConnection());
        initializeWithData();
    }

    private void configureForUserType() {
        switch (user.type) {
            case admin:
                menuSidePane.setStyle("-fx-background-color:  #c9160c");
                break;
            case manager:
                menuSidePane.setStyle("-fx-background-color:  #3ed111");
                deleteItemButton.setVisible(false);
                userMenuButton.setVisible(false);
                deleteInvoiceButton.setVisible(false);
                break;
            case worker:
                menuSidePane.setStyle("-fx-background-color:  #0787f0");
                addItemButton.setVisible(false);
                userMenuButton.setVisible(false);
                editItemCostButton.setVisible(false);
                deleteInvoiceButton.setVisible(false);
                editAvailableAmountButton.setVisible(false);
                deleteItemButton.setVisible(false);
                break;
        }
    }

    private void initializeWithData() {
        welcomeMessage.setText("Welcome \n" + user.name + "!");
        configureForUserType();
        try {
            reloadInvoices();
            clients.addAll(clientDatabase.getAllClients());
            clientsListView.getItems().addAll(clients);
            reloadItems();
            if(user.type == User.Type.admin) {
                reloadUsers();
            }
            invoicePane.toFront();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void handleMenuButtonAction(javafx.event.ActionEvent event) {
        if (event.getSource() == invoiceMenuButton) {
            invoicePane.toFront();
        } else if (event.getSource() == clientMenuButton) {
            clientPane.toFront();
        } else if (event.getSource() == itemMenuButton) {
            itemPane.toFront();
        }else if( event.getSource()== userMenuButton){
            usersPane.toFront();
        } else {
            logout();
        }
    }

    private void logout() {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("role_screen.fxml"));
        try {
            stage.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        RoleScreenController roleScreenController = fxmlLoader.getController();
        roleScreenController.initData(stage);
    }

    private void addInvoice() {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(App.class.getResource("add_invoice_screen.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(loader.load());
            AddInvoiceScreenController addInvoiceScreenController = loader.getController();
            addInvoiceScreenController.initData(database, user, invoicesListView);
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
            addClientScreenController.initData(database, user, clientsListView);
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(scene);
        stage.show();
    }

    private void addItem() {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(App.class.getResource("add_item_screen.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(loader.load());
            AddItemScreenController addItemScreenController = loader.getController();
            addItemScreenController.initData(database, user, itemsListView);
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(scene);
        stage.show();
    }

    private void editItemCost() {
        Item itemToChangePrice = itemsListView.getSelectionModel().getSelectedItem();
        if(itemToChangePrice!=null) {
            Dialog<Float> dialog = new Dialog<>();
            dialog.setTitle("Enter new Cost");
            ButtonType confirmButtonType = new ButtonType("Change cost", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(confirmButtonType, ButtonType.CANCEL);
            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(20, 150, 10, 10));
            TextField newCostTextField = new TextField();
            newCostTextField.setText(String.valueOf(itemToChangePrice.getNetAmount()));
            grid.add(newCostTextField, 0, 0);
            Node confirmButton = dialog.getDialogPane().lookupButton(confirmButtonType);
            confirmButton.setDisable(true);
            newCostTextField.textProperty().addListener((observable, oldValue, newValue) -> {
                try {
                    confirmButton.setDisable(newValue.trim().isEmpty() || Float.parseFloat(newValue) < 0);
                } catch (NumberFormatException ex) {
                    confirmButton.setDisable(true);
                }
            });
            dialog.getDialogPane().setContent(grid);
            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == confirmButtonType) {
                    try {
                        return Float.parseFloat(newCostTextField.getText());
                    } catch (NumberFormatException ex) {
                        return null;
                    }
                }
                return null;
            });

            Optional<Float> result = dialog.showAndWait();

            result.ifPresent(newCost -> {
                try {
                    if (!newCost.equals(itemToChangePrice.getNetAmount())) {

                        itemsDatabase.editItemCost(itemToChangePrice.getId(), newCost);
                        reloadItems();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    private void editAvailableAmount() {
        Item itemToChangeAvailableAmount = itemsListView.getSelectionModel().getSelectedItem();
        if(itemToChangeAvailableAmount!=null) {
            Dialog<Float> dialog = new Dialog<>();
            dialog.setTitle("Enter new Amount");
            ButtonType confirmButtonType = new ButtonType("Change amount", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(confirmButtonType, ButtonType.CANCEL);
            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(20, 150, 10, 10));
            TextField newAmountTextField = new TextField();
            newAmountTextField.setText(String.valueOf(itemToChangeAvailableAmount.getAvailableAmount()));
            grid.add(newAmountTextField, 0, 0);
            Node confirmButton = dialog.getDialogPane().lookupButton(confirmButtonType);
            confirmButton.setDisable(true);
            newAmountTextField.textProperty().addListener((observable, oldValue, newValue) -> {
                try {
                    confirmButton.setDisable(newValue.trim().isEmpty() || Float.parseFloat(newValue) < 0);
                } catch (NumberFormatException ex) {
                    confirmButton.setDisable(true);
                }
            });
            dialog.getDialogPane().setContent(grid);
            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == confirmButtonType) {
                    try {
                        return Float.parseFloat(newAmountTextField.getText());
                    } catch (NumberFormatException ex) {
                        return null;
                    }
                }
                return null;
            });

            Optional<Float> result = dialog.showAndWait();

            result.ifPresent(newAmount -> {
                try {
                    if (newAmount != itemToChangeAvailableAmount.getAvailableAmount()) {
                        itemsDatabase.editAvailableAmount(itemToChangeAvailableAmount.getId(), newAmount);
                        reloadItems();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
        }
    }
    private void reloadInvoices() {
        invoices.clear();
        invoicesListView.getItems().clear();
        try {
            invoices.addAll(invoiceDatabase.getAllInvoices());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        invoicesListView.getItems().addAll(invoices);
    }
    private void reloadItems() {
        System.out.println("reloading");
        items.clear();
        itemsListView.getItems().clear();
        try {
            items.addAll(itemsDatabase.getAllItems());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (showOnlyAvailableItemsToggleButton.selectedProperty().get()) {
            for (int i = items.size() - 1; i >= 0; i--) {
                if (items.get(i).getAvailableAmount() == 0) {
                    items.remove(i);
                }
            }
        }
        itemsListView.getItems().addAll(items);
    }
    private void reloadUsers() {
        users.clear();
        usersListView.getItems().clear();
        try {
            users.addAll(adminDatabase.getAllUsers());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        usersListView.getItems().addAll(users);
    }
    private void deleteItem(){
        Item itemToDelete = itemsListView.getSelectionModel().getSelectedItem();
        if(itemToDelete != null) {
            try {
                itemsDatabase.deleteItem(itemToDelete.getId());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            reloadItems();
        }
    }
    private void deleteInvoice(){
        Invoice invoiceToDelete = invoicesListView.getSelectionModel().getSelectedItem();
        if(invoiceToDelete != null){
            try {
                invoiceDatabase.deleteInvoice(invoiceToDelete.getInvoiceId());
                reloadInvoices();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }
    private void addUser(){

    }
    private void editUser(){

    }
    @FXML
    private void initialize() {
        addInvoiceButton.setOnAction(event -> addInvoice());
        addClientButton.setOnAction(event -> addClient());
        addItemButton.setOnAction(event -> addItem());
        deleteItemButton.setOnAction(event -> deleteItem());
        deleteInvoiceButton.setOnAction(event -> deleteInvoice());
        editAvailableAmountButton.setOnAction(event -> editAvailableAmount());
        editItemCostButton.setOnAction(event -> editItemCost());
        showOnlyAvailableItemsToggleButton.setOnAction(event -> reloadItems());
    }
}
