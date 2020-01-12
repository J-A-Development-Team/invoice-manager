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
import javafx.scene.text.Text;

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
    public Text netText;
    public Text grossText;
    float net = 0;
    float gross= 0;
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


        Optional<InvoiceElement> result = MyDialogFactory.getDialogForAddingItem(availableItems).showAndWait();

        result.ifPresent(invoiceElement -> {

            invoiceElements.add(invoiceElement);
            invoiceElementJFXListView.getItems().add(invoiceElement);
            calculateNetAndGross();
        });
    }
    private void calculateNetAndGross(){
        net = 0;
        gross =0;
        for(InvoiceElement invoiceElement : invoiceElements){
            net += invoiceElement.netCalculation();
            gross += invoiceElement.grossCalculation();
        }
        netText.setText(String.valueOf(net));
        grossText.setText(String.valueOf(gross));

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
