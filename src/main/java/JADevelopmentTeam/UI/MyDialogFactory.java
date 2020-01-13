package JADevelopmentTeam.UI;

import JADevelopmentTeam.InvoiceElement;
import JADevelopmentTeam.Item;
import com.jfoenix.controls.JFXComboBox;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

import java.util.ArrayList;

public abstract class MyDialogFactory {
    public static MyDialog<InvoiceElement> getDialogForAddingItem(ArrayList<Item> availableItems) {
        ButtonType confirmButtonType = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);

        GridPane grid = new GridPane();
        JFXComboBox<Item> itemComboBox = new JFXComboBox<>();
        itemComboBox.getItems().addAll(availableItems);
        javafx.scene.control.TextField quantityTextField = new javafx.scene.control.TextField();
        grid.add(new javafx.scene.control.Label("item:"), 0, 0);
        grid.add(itemComboBox, 1, 0);
        grid.add(new javafx.scene.control.Label("quantity:"), 0, 1);
        grid.add(quantityTextField, 1, 1);

        MyDialog<InvoiceElement> dialog = new MyDialog<>("Choose item", "Add", grid);
        dialog.setHeaderText("Please select desired item and enter amount");
        dialog.getConfirmButton().setDisable(true);
        quantityTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                dialog.getConfirmButton().setDisable(newValue.trim().isEmpty() || Float.parseFloat(newValue) > itemComboBox.getValue().getAvailableAmount() || Float.parseFloat(newValue) <= 0);

            } catch (NumberFormatException ex) {
                dialog.getConfirmButton().setDisable(true);
            }
        });
        itemComboBox.itemsProperty().addListener((observable, oldValue, newValue) -> {
            try {
                dialog.getConfirmButton().setDisable(newValue == null || Float.parseFloat(quantityTextField.getText()) > itemComboBox.getValue().getAvailableAmount());

            } catch (NumberFormatException ex) {
                dialog.getConfirmButton().setDisable(true);
            }
        });
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == dialog.getConfirmButtonType()) {
                try {
                    return new InvoiceElement(itemComboBox.getValue(), Float.parseFloat(quantityTextField.getText()));

                } catch (NumberFormatException ex) {
                    return null;
                }
            }
            return null;
        });
        return dialog;
    }

    public static MyDialog<Float> getDialogForEditingAvailableAmount(Item itemToChangeAvailableAmount) {
        return getDialogForItem(itemToChangeAvailableAmount, "Enter new Amount", "Change amount",String.valueOf(itemToChangeAvailableAmount.getAvailableAmount()));
    }

    public static MyDialog<Float> getDialogForEditingCost(Item itemForEditingCost) {
        return getDialogForItem(itemForEditingCost, "Enter new Cost", "Change cost",String.valueOf(itemForEditingCost.getNetAmount()));
    }

    public static MyDialog<Pair<String,String>> getDialogForEditingUser(){
        GridPane grid = new GridPane();
        TextField username = new TextField();
        username.setPromptText("username");
        PasswordField password = new PasswordField();
        password.setPromptText("password");
        PasswordField confirmPassword = new PasswordField();
        confirmPassword.setPromptText("confirm password");
        grid.add(username, 1, 0);
        grid.add(password, 1, 1);
        grid.add(confirmPassword, 1, 2);

        MyDialog<Pair<String, String>> dialog = new MyDialog<>("Set new username and password","Modify",grid);
        dialog.getConfirmButton().setDisable(true);
        username.textProperty().addListener((observable, oldValue, newValue) -> {
            dialog.getConfirmButton().setDisable(newValue.trim().isEmpty());
        });
        password.textProperty().addListener((observable, oldValue, newValue) -> {
            dialog.getConfirmButton().setDisable(newValue.trim().equals("") || !confirmPassword.getText().equals(newValue));
        });
        confirmPassword.textProperty().addListener((observable, oldValue, newValue) -> {
            dialog.getConfirmButton().setDisable(newValue.trim().equals("") || !password.getText().equals(newValue));
        });

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == dialog.getConfirmButtonType()) {
                return new Pair<>(username.getText(), password.getText());
            }
            return null;
        });
        return dialog;
    }

    private static void confirmButtonListenerForItem(MyDialog<Float> dialog, TextField newAmountTextField) {
        dialog.getConfirmButton().setDisable(true);
        newAmountTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                dialog.getConfirmButton().setDisable(newValue.trim().isEmpty() || Float.parseFloat(newValue) < 0);
            } catch (NumberFormatException ex) {
                dialog.getConfirmButton().setDisable(true);
            }
        });
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == dialog.getConfirmButtonType()) {
                try {
                    return Float.parseFloat(newAmountTextField.getText());
                } catch (NumberFormatException ex) {
                    return null;
                }
            }
            return null;
        });
    }

    private static MyDialog<Float> getDialogForItem(Item itemToChangeAvailableAmount, String title, String buttonText,String defaultText) {
        GridPane grid = new GridPane();
        TextField newAmountTextField = new TextField();
        newAmountTextField.setText(defaultText);
        grid.add(newAmountTextField, 0, 0);
        MyDialog<Float> dialog = new MyDialog<>(title, buttonText, grid);
        confirmButtonListenerForItem(dialog, newAmountTextField);
        return dialog;
    }
}
