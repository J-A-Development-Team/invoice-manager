package JADevelopmentTeam.mysql;


import JADevelopmentTeam.Invoice;
import JADevelopmentTeam.InvoiceBuilder;
import JADevelopmentTeam.InvoiceElement;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class InvoiceDatabase extends Database {
    private static InvoiceBuilder invoiceBuilder;

    public InvoiceDatabase(String user, String password) throws SQLException {
        super(user, password);
    }

    public InvoiceDatabase(Connection connection) {
        super(connection);
        invoiceBuilder = new InvoiceBuilder();
    }


    private ArrayList<Invoice> resultToInvoices(ResultSet rs) throws SQLException {
        ArrayList<Invoice> invoices = new ArrayList<>();
        while (rs.next()) {
            invoiceBuilder.setElements(InvoiceElementDatabase.getAllInvoiceElements(rs.getInt("invoice_id"), connection));
            invoices.add(invoiceBuilder.createNewInvoice(ClientDatabase.resultToClient(rs), rs.getDate("date"), rs.getInt("invoice_id")));
        }
        rs.beforeFirst();
        rs.next();
        return invoices;
    }

    public void addInvoice(Invoice invoice, int userID, int clientID) throws SQLException {
        connection.setAutoCommit(false);
        try {
            preparedStatement = connection.prepareStatement("call add_invoice (?,?,?)");
            preparedStatement.setInt(3, userID);
            preparedStatement.setInt(2, clientID);
            preparedStatement.setDate(1, new Date(invoice.getDate().getTime()));
            preparedStatement.executeUpdate();
            int id = lastInvoiceId();
            for (InvoiceElement element :
                    invoice.getElements()) {
                InvoiceElementDatabase.addInvoiceElement(element, id, connection);
            }
            connection.commit();
        }
        catch (SQLException e){
            connection.rollback();
            connection.setAutoCommit(true);
            throw e;
        }
        connection.setAutoCommit(true);
    }

    public ArrayList<Invoice> getAllInvoices() throws SQLException {
        ResultSet rs = connection.prepareStatement("call get_all_invoices()").executeQuery();
        return resultToInvoices(rs);
    }

    private int lastInvoiceId() throws SQLException {
        ResultSet rs = connection.createStatement().executeQuery("SELECT MAX(invoice_id) FROM invoice");
        rs.next();
        return rs.getInt(1);
    }

    public void deleteInvoice(int invoiceId) throws SQLException {
        preparedStatement = connection.prepareStatement("call delete_invoice(?)");
        preparedStatement.setInt(1, invoiceId);
        preparedStatement.executeUpdate();
    }
}
