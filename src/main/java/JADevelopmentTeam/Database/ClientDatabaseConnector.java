package JADevelopmentTeam.Database;


import JADevelopmentTeam.Client;
import JADevelopmentTeam.ClientBuilder;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ClientDatabaseConnector {
    private MySQLDatabase database;
    private ResultSet resultSet;

    public ClientDatabaseConnector(MySQLDatabase database) {
        this.database = database;
    }

    public Client getClientById(int id) throws SQLException {
        resultSet = database.userStatement.executeQuery("select * from invoiceManager.clients where id='" + id + "';");
        resultSet.next();
        return writeClient(resultSet);
    }

    public Client getClientByInvoiceNumber(int invoiceNumber) throws SQLException {
        resultSet = database.userStatement.executeQuery("select * from invoiceManager.clients where id '" + invoiceNumber + "';");
        //TODO complete getClientByInvoiceNumber
        resultSet.next();
        return writeClient(resultSet);
    }

    public Client getClientByName(String name) throws SQLException {
        resultSet = database.userStatement.executeQuery("select * from invoiceManager.clients where name='" + name + "';");
        resultSet.next();
        return writeClient(resultSet);
    }

    public ArrayList<Client> getAllClients() throws SQLException {
        resultSet = database.userStatement.executeQuery("select * from invoiceManager.clients;");
        return writeClients(resultSet);

    }

    public void addClient(Client client) throws SQLException {
        String query = " insert into clients (name, addressFirstLine, addressSecondLine, postCode, city,nip)"
                + " values (?, ?, ?, ?, ?,?)";
        PreparedStatement preparedStatement = database.connection.prepareStatement(query);
        preparedStatement.setString(1, client.getName());
        preparedStatement.setString(2, client.getAddressFirstLine());
        preparedStatement.setString(3, client.getAddressSecondLine());
        preparedStatement.setString(4, client.getPostCode());
        preparedStatement.setString(5, client.getCity());
        preparedStatement.setString(6, client.getNip());
        preparedStatement.execute();
    }

    private Client writeClient(ResultSet resultSet) throws SQLException {
        ClientBuilder clientBuilder = new ClientBuilder();
        clientBuilder.setId(resultSet.getInt("id"));
        clientBuilder.setName(resultSet.getString("name"));
        clientBuilder.setAddressFirstLine(resultSet.getString("addressFirstLine"));
        clientBuilder.setAddressSecondLine(resultSet.getString("addressSecondLine"));
        clientBuilder.setPostCode(resultSet.getString("postCode"));
        clientBuilder.setCity(resultSet.getString("city"));
        clientBuilder.setNip(resultSet.getString("nip"));
        return clientBuilder.getClient();
    }

    private ArrayList<Client> writeClients(ResultSet resultSet) throws SQLException {
        ArrayList<Client> clients = new ArrayList<>();
        while (resultSet.next()) {
            clients.add(writeClient(resultSet));
        }
        return clients;
    }
}
