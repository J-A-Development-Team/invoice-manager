package JADevelopmentTeam;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

public class WorkerMenuScreenController {
    @FXML
    private Pane invoicePane,clientPane;
    @FXML
    private JFXButton invoiceMenuButton,clientMenuButton;


    public void handleMenuButtonAction(javafx.event.ActionEvent event) {
        if(event.getSource()==invoiceMenuButton){
            invoicePane.toFront();
        }else{
            clientPane.toFront();
        }
    }
}
