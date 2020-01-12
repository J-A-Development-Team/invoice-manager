package JADevelopmentTeam.UI;

import JADevelopmentTeam.*;
import JADevelopmentTeam.mysql.ClientDatabase;
import JADevelopmentTeam.mysql.Database;
import JADevelopmentTeam.mysql.InvoiceDatabase;
import JADevelopmentTeam.mysql.ItemDatabase;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

public class AddInvoiceScreenController {
    public JFXComboBox<Client> clientComboBox;
    public JFXButton addItemButton;
    public JFXListView<InvoiceElement> invoiceElementJFXListView = new JFXListView<>();
    public JFXButton generateButton;
    public DatePicker datePicker;
    Database database;
    ClientDatabase clientDatabase;
    ItemDatabase itemDatabase;
    InvoiceDatabase invoiceDatabase;
    MenuScreenController menuScreenController;
    ArrayList<InvoiceElement> invoiceElements = new ArrayList<>();
    User user = new User();

    public void initData(Database dataBase, User user,MenuScreenController menuScreenController) {
        this.database = dataBase;
        this.user = user;
        this.menuScreenController = menuScreenController;
        clientDatabase = new ClientDatabase(database.getConnection());
        itemDatabase = new ItemDatabase(database.getConnection());
        invoiceDatabase = new InvoiceDatabase(database.getConnection());
        initializeWithData();
    }

    private void initializeWithData() {
        try {
            clientComboBox.getItems().addAll(clientDatabase.getAllClients());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void showAddItemDialog() {
        ArrayList<Item> availableItems = null;
        try {
            availableItems = new ArrayList<>(itemDatabase.getAllItems());
            for (int i = availableItems.size()-1;i>=0;i--){
                if(availableItems.get(i).getAvailableAmount()==0){
                    availableItems.remove(i);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Dialog<InvoiceElement> dialog = new Dialog<>();
        dialog.setTitle("Choose item");
        dialog.setHeaderText("Please select desired item and enter amount");
        ButtonType confirmButtonType = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(confirmButtonType, ButtonType.CANCEL);
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        JFXComboBox<Item> itemComboBox = new JFXComboBox<>();
        itemComboBox.getItems().addAll(availableItems);
        TextField quantityTextField = new TextField();
        grid.add(new Label("item:"), 0, 0);
        grid.add(itemComboBox, 1, 0);
        grid.add(new Label("quantity:"), 0, 1);
        grid.add(quantityTextField, 1, 1);
        Node confirmButton = dialog.getDialogPane().lookupButton(confirmButtonType);
        confirmButton.setDisable(true);
        quantityTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                confirmButton.setDisable(newValue.trim().isEmpty() || Float.parseFloat(newValue) > itemComboBox.getValue().getAvailableAmount() || Float.parseFloat(newValue) <=0);

            } catch (NumberFormatException ex) {
                confirmButton.setDisable(true);
            }
        });
        itemComboBox.itemsProperty().addListener((observable, oldValue, newValue) -> {
            try {
                confirmButton.setDisable(newValue == null || Float.parseFloat(quantityTextField.getText()) > itemComboBox.getValue().getAvailableAmount());

            } catch (NumberFormatException ex) {
                confirmButton.setDisable(true);
            }
        });
        dialog.getDialogPane().setContent(grid);
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == confirmButtonType) {
                try {
                    return new InvoiceElement(itemComboBox.getValue(), Float.parseFloat(quantityTextField.getText()));

                } catch (NumberFormatException ex) {
                    return null;
                }
            }
            return null;
        });

        Optional<InvoiceElement> result = dialog.showAndWait();

        result.ifPresent(invoiceElement -> {
            invoiceElements.add(invoiceElement);
            invoiceElementJFXListView.getItems().add(invoiceElement);
        });
    }

    private void generateNewInvoice() {
        InvoiceBuilder invoiceBuilder = new InvoiceBuilder();
        invoiceBuilder.setElements(invoiceElements);
        LocalDate localDate = datePicker.getValue();
        Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
        Date date = Date.from(instant);
        Invoice invoice = invoiceBuilder.createNewInvoice(clientComboBox.getValue(), date, 0);
        try {
            invoiceDatabase.addInvoice(invoice, user.getId(), clientComboBox.getValue().id);
            menuScreenController.reloadInvoices();
            menuScreenController.reloadItems();
            addItemButton.getScene().getWindow().hide();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void initialize() {
        addItemButton.setOnAction(event -> showAddItemDialog());
        generateButton.setOnAction(event -> generateNewInvoice());


    }
}
