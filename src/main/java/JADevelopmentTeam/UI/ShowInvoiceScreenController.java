package JADevelopmentTeam.UI;

import JADevelopmentTeam.Invoice;
import JADevelopmentTeam.InvoiceElement;
import JADevelopmentTeam.User;
import JADevelopmentTeam.mysql.Database;
import JADevelopmentTeam.mysql.InvoiceDatabase;
import JADevelopmentTeam.mysql.InvoiceElementDatabase;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import java.sql.SQLException;
import java.util.ArrayList;

public class ShowInvoiceScreenController {
    public Label invoiceNumberLabel;
    public JFXListView<InvoiceElement> invoiceElementsListView;
    public JFXButton deleteInvoiceElementButton;
    public JFXButton cancelButton;
    public Label clientText;
    public Text netText;
    public Text grossText;
    public JFXButton saveChangesButton;
    ArrayList<InvoiceElement> invoiceElements = new ArrayList<>();
    ArrayList<InvoiceElement> elementsToDelete = new ArrayList<>();
    Database database;
    InvoiceDatabase invoiceDatabase;
    InvoiceElementDatabase invoiceElementDatabase;
    Invoice invoice;
    User user = new User();
    MenuScreenController menuScreenController;
    public void initData(Database database, User user, Invoice invoice, MenuScreenController menuScreenController) {
        this.database = database;
        this.user = user;
        this.invoice = invoice;
        this.menuScreenController = menuScreenController;
        invoiceDatabase = new InvoiceDatabase(database.getConnection());
        invoiceElementDatabase = new InvoiceElementDatabase(database.getConnection());
        invoiceElements.addAll(invoice.getElements());
        initWithData();
    }

    private void initWithData() {
        invoiceNumberLabel.setText("Invoice nr " + invoice.getInvoiceId());
        netText.setText(String.valueOf(invoice.getFullNet()));
        grossText.setText(String.valueOf(invoice.getFullGross()));
        clientText.setText(invoice.getClientName()+"\n"+
                           invoice.getClientNIP()+"\n"+
                           invoice.getClientStreetAndNumber()+"\n"+
                           invoice.getClientPostCode()+" "+ invoice.getClientCity());
        reloadElements();
    }

    private void reloadElements() {
        invoiceElementsListView.getItems().clear();
        invoiceElementsListView.getItems().addAll(invoiceElements);
    }

    private void deleteInvoiceElement() {
        InvoiceElement elementToDelete = invoiceElementsListView.getSelectionModel().getSelectedItem();
        if (elementToDelete != null) {
            elementsToDelete.add(elementToDelete);
            invoiceElements.remove(elementToDelete);
            saveChangesButton.setVisible(true);
            cancelButton.setVisible(true);
            reloadElements();
        }
    }

    private void confirmChanges() {
        try {
            for (InvoiceElement invoiceElement : elementsToDelete) {
                invoiceElementDatabase.deleteInvoiceElement(invoice.getInvoiceId(), invoiceElement.getItem().getId(), invoiceElement.getQuantity());
            }
            menuScreenController.reloadInvoices();
            menuScreenController.reloadItems();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        saveChangesButton.getScene().getWindow().hide();
    }

    private void cancelChanges() {
        saveChangesButton.getScene().getWindow().hide();
    }

    @FXML
    private void initialize() {
        deleteInvoiceElementButton.setOnAction(event -> deleteInvoiceElement());
        saveChangesButton.setOnAction(event -> confirmChanges());
        cancelButton.setOnAction(event -> cancelChanges());

    }
}
