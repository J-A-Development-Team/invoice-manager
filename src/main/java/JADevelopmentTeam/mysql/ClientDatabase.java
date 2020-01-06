package JADevelopmentTeam.mysql;



import JADevelopmentTeam.Client;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ClientDatabase extends Database {


    ClientDatabase(String user, String password) {
        super(user, password);
    }

    public void addClient(Client client) throws SQLException {
        preparedStatement = connection.prepareStatement("INSERT INTO client VALUES (id,?,?,?,?,?)");
        preparedStatement.setString(1, client.name);
        preparedStatement.setString(2, client.NIP);
        preparedStatement.setString(3, client.city);
        preparedStatement.setString(4, client.streetAndNumber);
        preparedStatement.setString(5, client.postcode);
        preparedStatement.executeUpdate();
    }

    public Client getClientByID(int id) throws SQLException {
        ResultSet rs = statement.executeQuery("select * from client WHERE id ='" + id+"'");
        rs.next();
        return resultToClient(rs);
    }

    public Client getClientByName(String name) throws SQLException {
        ResultSet rs = statement.executeQuery("select * from client WHERE name ='" + name+"'");
        rs.next();
        return resultToClient(rs);
    }


    public ArrayList<Client> getAllClients() throws SQLException {
        ArrayList<Client> clients = new ArrayList<>();
        ResultSet rs = statement.executeQuery("select * from client");
        while (rs.next())
            clients.add(resultToClient(rs));
        return clients;
    }

    public void deleteClientByID(int id) throws SQLException {
        statement.executeUpdate("DELETE client,ie,i\n" +
                "FROM client\n" +
                "LEFT JOIN invoice i on client.id = i.client_id\n" +
                "LEFT JOIN invoice_element ie on i.invoice_id = ie.invoice_id\n" +
                "WHERE client.id='" + id+"'");
    }

    public void deleteClientByName(String name) throws SQLException {
        statement.executeUpdate("DELETE client,ie,i\n" +
                "FROM client\n" +
                "LEFT JOIN invoice i on client.id = i.client_id\n" +
                "LEFT JOIN invoice_element ie on i.invoice_id = ie.invoice_id\n" +
                "WHERE name ='" + name+"'");
    }

    static Client resultToClient(ResultSet rs) throws SQLException {
        return new Client(rs.getInt("id"), rs.getString("name"), rs.getString("NIP"), rs.getString("city"), rs.getString("street_and_number"), rs.getString("postcode"));

    }
    public static int idClient(String NIP, Statement statement) throws SQLException {
        ResultSet rs = statement.executeQuery("SELECT * FROM client WHERE NIP ='"+NIP+"'");
        rs.next();
        return resultToClient(rs).id;
    }
        public int idClient(String NIP) throws SQLException {
        return idClient(NIP,statement);
    }

}
