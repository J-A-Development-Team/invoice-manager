package JADevelopmentTeam.UI;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.layout.GridPane;


public class MyDialog<T> extends Dialog<T> {
    private Node confirmButton;
    private ButtonType confirmButtonType;

    public MyDialog(String title, String buttonText, GridPane gridPane) {
        super();
        setTitle(title);
        this.confirmButtonType = new ButtonType(buttonText, ButtonBar.ButtonData.OK_DONE);
        getDialogPane().getButtonTypes().addAll(confirmButtonType, ButtonType.CANCEL);
        this.confirmButton = getDialogPane().lookupButton(confirmButtonType);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 150, 10, 10));
        getDialogPane().setContent(gridPane);
    }

    public Node getConfirmButton() {
        return confirmButton;
    }

    public ButtonType getConfirmButtonType() {
        return confirmButtonType;
    }
}
