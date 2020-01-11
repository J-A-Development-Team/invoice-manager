package JADevelopmentTeam;

import JADevelopmentTeam.mysql.Database;
import JADevelopmentTeam.mysql.ItemDatabase;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class AddItemScreenController {
    public TextField itemNameTextField;
    public TextField itemDescriptionTextField;
    public TextField priceTextField;
    public TextField availableAmountTextField;
    public JFXButton addItemButton;
    public JFXComboBox<TaxManager.taxType> taxTypeComboBox;
    public JFXListView<Item> items;
    ItemDatabase itemDatabase;
    Database database;
    User user = new User();
    private String itemName;
    private String itemDescription;
    private float itemPrice;
    private float availableAmount;
    private TaxManager.taxType taxType;

    public void initData(Database dataBase, User user, JFXListView<Item> items) {
        this.database = dataBase;
        this.user = user;
        this.items = items;
        itemDatabase = new ItemDatabase(database.getConnection());
        taxTypeComboBox.getItems().add(TaxManager.taxType.o0);
        taxTypeComboBox.getItems().add(TaxManager.taxType.o5);
        taxTypeComboBox.getItems().add(TaxManager.taxType.o8);
        taxTypeComboBox.getItems().add(TaxManager.taxType.o23);
        taxTypeComboBox.getItems().add(TaxManager.taxType.zw);
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
        taxType = taxTypeComboBox.getValue();

    }

    private boolean checkIfAllFiled() {
        getDataFromTextFields();
        if (itemName.equals(""))
            return true;
        if(itemPrice<0)
            return true;
        if(availableAmount<0)
            return true;
        return false;
    }

    private void addNewItem() {
        Item item = new Item(itemName, itemPrice, taxType, itemDescription, 0, availableAmount);
        try {
            itemDatabase.add_item(item);
            items.getItems().clear();
            items.getItems().addAll(itemDatabase.getAllItems());
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


    }
}
