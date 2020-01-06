package JADevelopmentTeam.Database;



import JADevelopmentTeam.Client;
import JADevelopmentTeam.Invoice;
import JADevelopmentTeam.InvoiceBuilder;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class InvoiceDatabaseConnector {
    private MySQLDatabase database;
    private ResultSet resultSet;
    private ClientDatabaseConnector clientDatabaseConnector;

    public InvoiceDatabaseConnector(MySQLDatabase database, ClientDatabaseConnector clientDatabaseConnector) {
        this.database = database;
        this.clientDatabaseConnector = clientDatabaseConnector;
    }

    public Invoice getInvoiceByNumber(int number) throws SQLException {
        resultSet = database.invoiceStatement.executeQuery("select * from invoiceManager.invoices where number='" + number + "';");
        resultSet.next();
        return writeInvoice(resultSet);
    }

    public ArrayList<Invoice> getAllInvoices() throws SQLException {
        resultSet = database.invoiceStatement.executeQuery("select * from invoiceManager.invoices;");
        return writeInvoices(resultSet);

    }

    public ArrayList<Invoice> getInvoicesByClient(Client client) throws SQLException {
        resultSet = database.invoiceStatement.executeQuery("select * from invoiceManager.invoices where clientId ='" + client.getId() + "';");
        return writeInvoices(resultSet);
    }

    public void addInvoice(Invoice invoice) throws SQLException {
        String query = " insert into invoices (number, clientId, date, netTotal, grossTotal,elements)"
                + " values (?, ?, ?, ?, ?,?)";
        PreparedStatement preparedStatement = database.connection.prepareStatement(query);
        preparedStatement.setInt(1, invoice.getNumber());
        preparedStatement.setInt(2, invoice.getClientId());
        preparedStatement.setDate(3, new Date(invoice.getDate().getTime()));
        preparedStatement.setFloat(4, invoice.getTotalNet());
        preparedStatement.setFloat(5, invoice.getTotalGross());
        preparedStatement.setString(6, invoice.getElementsAsJson());
        preparedStatement.execute();
    }

    public int getNextAvailableInvoiceNumber() throws SQLException {
        resultSet = database.invoiceStatement.executeQuery("select max(number) from invoiceManager.invoices");
        resultSet.next();
        return resultSet.getInt("max(number)") + 1;
    }

    private Invoice writeInvoice(ResultSet resultSet) throws SQLException {
        InvoiceBuilder invoiceBuilder = new InvoiceBuilder();
        invoiceBuilder.setClient(clientDatabaseConnector.getClientById(this.resultSet.getInt("clientId")));
        invoiceBuilder.setDate(resultSet.getDate("date"));
        invoiceBuilder.setTotalGross(resultSet.getFloat("grossTotal"));
        invoiceBuilder.setTotalNet(resultSet.getFloat("netTotal"));
        invoiceBuilder.setElements(InvoiceBuilder.convertInvoiceElementsFromJson(resultSet.getString("elements")));
        return invoiceBuilder.getInvoice();
    }

    private ArrayList<Invoice> writeInvoices(ResultSet resultSet) throws SQLException {
        ArrayList<Invoice> invoices = new ArrayList<>();
        while (resultSet.next()) {
            invoices.add(writeInvoice(resultSet));
        }
        return invoices;
    }
}
