package JADevelopmentTeam.mysql;


import JADevelopmentTeam.Invoice;
import JADevelopmentTeam.InvoiceBuilder;
import JADevelopmentTeam.InvoiceElement;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class InvoiceDatabase extends Database {
    private static InvoiceBuilder invoiceBuilder;

    public InvoiceDatabase(String user,String password) {
        super(user, password);
        invoiceBuilder = new InvoiceBuilder();
    }

    public Invoice getInvoiceById(int id) throws SQLException {

        ResultSet rs = statement.executeQuery("SELECT *\n" +
                "FROM invoice\n" +
                "INNER JOIN invoice_element ie on invoice.invoice_id = ie.invoice_id\n" +
                "INNER JOIN client c on invoice.client_id = c.id\n" +
                "INNER JOIN item i on ie.item_id = i.item_id" +
                " ie.invoice_id ='" + id+"'");
        return resultToInvoice(rs);
    }

    private static Invoice resultToInvoice(ResultSet rs) throws SQLException {
        while (rs.next()) {
            invoiceBuilder.addElement(InvoiceElementDatabase.resultToInvoiceElement(rs));
        }
        rs.beforeFirst();
        rs.next();
        return invoiceBuilder.createNewInvoice(ClientDatabase.resultToClient(rs), rs.getDate("date"), rs.getInt("invoice_id"));
    }

    public void addInvoice(Invoice invoice,int userID) throws SQLException {
        preparedStatement = connection.prepareStatement("INSERT INTO invoice VALUES (invoice_id,?,date,?,modified,?)");
        preparedStatement.setInt(1, ClientDatabase.idClient(invoice.getClientNIP(),statement));
        preparedStatement.setDate(2, new Date(invoice.getDate().getTime()));
        preparedStatement.executeUpdate();
        int id = lastInvoiceId();
        for (InvoiceElement element :
                invoice.getElements()) {
            InvoiceElementDatabase.addInvoiceElement(element, id,connection);
        }
    }

    private int lastInvoiceId() throws SQLException {
        ResultSet rs = statement.executeQuery("SELECT MAX(invoice_id) FROM invoice");
        rs.next();
        return rs.getInt(1);
    }

    public void deleteInvoice(int invoiceId) throws SQLException {
        statement.executeUpdate("DELETE invoice\n" +
                "FROM invoice\n" +
                "WHERE invoice.invoice_id ='" + invoiceId+"'");
    }

    public ArrayList<Integer> getAllClientInvoiceName(String name) throws SQLException {
        ArrayList<Integer> invoices = new ArrayList<>();
        ResultSet rs = statement.executeQuery("SELECT invoice.invoice_id\n" +
                "FROM invoice\n" +
                "INNER JOIN client c on invoice.client_id = c.id\n" +
                "WHERE name = '" + name + "'");
        while (rs.next())
            invoices.add(rs.getInt(1));
        return invoices;
    }
}
