package JADevelopmentTeam.mysql;


import JADevelopmentTeam.InvoiceElement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InvoiceElementDatabase extends Database {
    InvoiceElementDatabase(String user, String password) {
        super(user, password);
    }


    public void deleteInvoiceElement(int invoice_id, int itemID, float quantity) throws SQLException {
        statement.executeUpdate("DELETE invoice_element\n" +
                "FROM invoice_element\n" +
                "WHERE invoice_id = '" + invoice_id + "'\n" +
                "AND item_id = '" + itemID + "'" +
                "AND quantity = '"+ quantity +"'");
    }

    static InvoiceElement resultToInvoiceElement(ResultSet rs) throws SQLException {
        return new InvoiceElement(ItemDatabase.resultToItem(rs), rs.getFloat("quantity"));
    }

    public static void addInvoiceElement(InvoiceElement invoiceElement, int invoiceId, Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO invoice_element VALUES (?,?,?)");
        preparedStatement.setInt(1, invoiceId);
        preparedStatement.setInt(2, invoiceElement.getItem().getId());
        preparedStatement.setFloat(3, invoiceElement.getQuantity());
        preparedStatement.executeUpdate();
    }

    public void addInvoiceElement(InvoiceElement invoiceElement, int invoiceId) throws SQLException {
        addInvoiceElement(invoiceElement, invoiceId,connection);
    }
}
