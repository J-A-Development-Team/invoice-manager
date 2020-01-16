package JADevelopmentTeam.mysql;


import JADevelopmentTeam.InvoiceElement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class InvoiceElementDatabase {
    Connection connection;

    public InvoiceElementDatabase(Connection connection) {
        this.connection = connection;
    }

    public void deleteInvoiceElement(int invoice_id, int itemID, float quantity) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("call delete_invoice_element(?,?,?)");
        preparedStatement.setInt(1, invoice_id);
        preparedStatement.setInt(2, itemID);
        preparedStatement.setFloat(3, quantity);
        preparedStatement.executeUpdate();
    }

    static InvoiceElement resultToInvoiceElement(ResultSet rs) throws SQLException {
        return new InvoiceElement(ItemDatabase.resultToItem(rs), rs.getFloat("quantity"));
    }

    public static void addInvoiceElement(InvoiceElement invoiceElement, int invoiceId, Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("call add_invoice_element(?,?,?)");
        preparedStatement.setInt(1, invoiceId);
        preparedStatement.setInt(2, invoiceElement.getItem().getId());
        preparedStatement.setFloat(3, invoiceElement.getQuantity());
        preparedStatement.executeUpdate();
    }

    public void addInvoiceElement(InvoiceElement invoiceElement, int invoiceId) throws SQLException {
        addInvoiceElement(invoiceElement, invoiceId, connection);
    }

    public ArrayList<InvoiceElement> getAllInvoiceElements(int invoiceId) throws SQLException {
        return getAllInvoiceElements(invoiceId,connection);
    }
    public static ArrayList<InvoiceElement> getAllInvoiceElements(int invoiceId, Connection connection) throws SQLException {
        ArrayList<InvoiceElement> invoiceElements = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement("call get_all_invoice_elements_for_invoice_id(?)");
        preparedStatement.setInt(1, invoiceId);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            invoiceElements.add(resultToInvoiceElement(rs));
        }
        return invoiceElements;
    }
}
