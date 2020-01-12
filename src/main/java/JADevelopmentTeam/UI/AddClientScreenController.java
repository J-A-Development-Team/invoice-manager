package JADevelopmentTeam.UI;

import JADevelopmentTeam.Client;
import JADevelopmentTeam.User;
import JADevelopmentTeam.mysql.ClientDatabase;
import JADevelopmentTeam.mysql.Database;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import java.sql.SQLException;
import java.util.ArrayList;

public class AddClientScreenController {
    public TextField clientNameTextField;
    public TextField NIPTextField;
    public TextField streetAndNumberTextField;
    public TextField postCodeTextField;
    public TextField cityTextField;
    public JFXButton addClientButton;
    ClientDatabase clientDatabase;
    Database database;
    User user = new User();
    MenuScreenController menuScreenController;
    private String clientName;
    private String NIP;
    private String streetAndNumber;
    private String postCode;
    private String city;
    public void initData(Database dataBase, User user, MenuScreenController menuScreenController) {
        this.database = dataBase;
        this.user = user;
        this.menuScreenController = menuScreenController;
        clientDatabase = new ClientDatabase(database.getConnection());
    }

    private void getDataFromTextFields() {
        clientName = clientNameTextField.getText();
        NIP = NIPTextField.getText();
        streetAndNumber = streetAndNumberTextField.getText();
        postCode = postCodeTextField.getText();
        city = cityTextField.getText();
    }

    private boolean checkIfAllFiled() {
        getDataFromTextFields();
        ArrayList<String> strings = new ArrayList<>();
        strings.add(clientName);
        strings.add(NIP);
        strings.add(streetAndNumber);
        strings.add(postCode);
        strings.add(city);
        for (String string : strings) {
            if (string.equals("")) {
                return true;
            }
        }
        return false;
    }

    private void addNewClient() {
        Client client = new Client(0,clientName,NIP,city,streetAndNumber,postCode);
        try {
            clientDatabase.addClient(client);
            menuScreenController.reloadClients();
            addClientButton.getScene().getWindow().hide();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void initialize() {
        addClientButton.setOnAction(event -> addNewClient());
        clientNameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            addClientButton.setDisable(checkIfAllFiled());
        });
        NIPTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            addClientButton.setDisable(checkIfAllFiled());
        });
        streetAndNumberTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            addClientButton.setDisable(checkIfAllFiled());
        });
        postCodeTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            addClientButton.setDisable(checkIfAllFiled());
        });
        cityTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            addClientButton.setDisable(checkIfAllFiled());
        });

    }
}

