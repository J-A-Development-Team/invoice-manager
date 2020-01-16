package JADevelopmentTeam.mysql;


import JADevelopmentTeam.Client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ClientDatabase {
    Connection connection;

    public ClientDatabase(Connection connection) {
        this.connection = connection;
    }

    public void addClient(Client client) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("call  add_client(?,?,?,?,?)");
        preparedStatement.setString(1, client.name);
        preparedStatement.setString(2, client.NIP);
        preparedStatement.setString(3, client.city);
        preparedStatement.setString(4, client.streetAndNumber);
        preparedStatement.setString(5, client.postcode);
        preparedStatement.executeUpdate();
    }


    public ArrayList<Client> getAllClients() throws SQLException {
        ArrayList<Client> clients = new ArrayList<>();
        ResultSet rs = connection.createStatement().executeQuery("call get_all_clients()");
        while (rs.next())
            clients.add(resultToClient(rs));
        return clients;
    }

    static Client resultToClient(ResultSet rs) throws SQLException {
        return new Client(rs.getInt("id"), rs.getString("name"), rs.getString("NIP"), rs.getString("city"), rs.getString("street_and_number"), rs.getString("postcode"));
    }


}
