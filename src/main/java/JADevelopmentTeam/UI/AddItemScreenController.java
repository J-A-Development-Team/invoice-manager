package JADevelopmentTeam.UI;

import JADevelopmentTeam.Item;
import JADevelopmentTeam.TaxManager;
import JADevelopmentTeam.User;
import JADevelopmentTeam.mysql.Database;
import JADevelopmentTeam.mysql.ItemDatabase;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class AddItemScreenController {
    public TextField itemNameTextField;
    public TextField itemDescriptionTextField;
    public TextField priceTextField;
    public TextField availableAmountTextField;
    public JFXButton addItemButton;
    public JFXComboBox<String> taxTypeComboBox;
    ItemDatabase itemDatabase;
    Database database;
    User user = new User();
    MenuScreenController menuScreenController;
    private String itemName;
    private String itemDescription;
    private float itemPrice;
    private float availableAmount;
    private TaxManager.taxType taxType;

    public void initData(Database dataBase, User user, MenuScreenController menuScreenController) {
        this.database = dataBase;
        this.user = user;
        this.menuScreenController = menuScreenController;
        itemDatabase = new ItemDatabase(database.getConnection());
        taxTypeComboBox.getItems().add("0%");
        taxTypeComboBox.getItems().add("5%");
        taxTypeComboBox.getItems().add("8%");
        taxTypeComboBox.getItems().add("23%");
        taxTypeComboBox.getItems().add("zw");
    }

    private void getDataFromTextFields() {
        itemName = itemNameTextField.getText();
        itemDescription = itemDescriptionTextField.getText();
        try {
            itemPrice = Float.parseFloat(priceTextField.getText());
            availableAmount = Float.parseFloat(availableAmountTextField.getText());
        } catch (NumberFormatException ex) {
            itemPrice = -1;
            availableAmount = -1;
        }
        if(taxTypeComboBox.getValue()!=null){
            taxType = TaxManager.stringToTax(taxTypeComboBox.getValue());

        }

    }

    private boolean checkIfAllFiled() {
        getDataFromTextFields();
        if (itemName.equals(""))
            return true;
        if (itemPrice < 0)
            return true;
        if (availableAmount < 0)
            return true;
        if (taxTypeComboBox.getValue() ==null)
            return true;
        return false;
    }

    private void addNewItem() {
        getDataFromTextFields();
        Item item = new Item(itemName, itemPrice, taxType, itemDescription, 0, availableAmount);
        try {
            itemDatabase.add_item(item);
            menuScreenController.reloadItems();
            addItemButton.getScene().getWindow().hide();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void initialize() {
        addItemButton.setOnAction(event -> addNewItem());
        itemNameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            addItemButton.setDisable(checkIfAllFiled());
        });
        itemDescriptionTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            addItemButton.setDisable(checkIfAllFiled());
        });
        priceTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            addItemButton.setDisable(checkIfAllFiled());
        });
        availableAmountTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            addItemButton.setDisable(checkIfAllFiled());
        });
        taxTypeComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            addItemButton.setDisable(checkIfAllFiled());
        });

    }
}
