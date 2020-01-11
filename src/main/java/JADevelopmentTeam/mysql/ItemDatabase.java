package JADevelopmentTeam.mysql;

import JADevelopmentTeam.Item;
import JADevelopmentTeam.TaxManager;

import java.sql.*;
import java.util.ArrayList;

public class ItemDatabase extends Database {
    public ItemDatabase(Connection connection) {
        super(connection);
    }

    public ItemDatabase(String user, String password) throws SQLException {
        super(user, password);
    }

    public static Item resultToItem(ResultSet rs) throws SQLException {
        return new Item(rs.getString("name"),rs.getFloat("cost"), TaxManager.stringToTax(rs.getString("tax")), rs.getString("description"), rs.getInt("item_id"), rs.getInt("available_amount"));
    }
    public void add_item(Item item) throws SQLException {
        preparedStatement = connection.prepareStatement("call add_item (?,?,?,?,?)");
        preparedStatement.setString(1,item.getName());
        preparedStatement.setString(3,item.getDescription());
        preparedStatement.setString(2,TaxManager.taxToString(item.getTaxType()));
        preparedStatement.setFloat(4,item.getNetAmount());
        preparedStatement.setFloat(5,item.getAvailableAmount());
        preparedStatement.executeUpdate();
    }
    public void deleteItem(int id) throws SQLException {
        preparedStatement = connection.prepareStatement("call delete_item(?)");
        preparedStatement.setInt(1,id);
        preparedStatement.executeUpdate();
    }
    public void editAvailableAmount(int id,float availableAmount) throws SQLException {
        preparedStatement = connection.prepareStatement("call edit_available_amount(?,?)");
        preparedStatement.setInt(1,id);
        preparedStatement.setFloat(2,availableAmount);
    }
    public void editItemCost(int id,float newCost) throws SQLException {
        preparedStatement = connection.prepareStatement("call edit_item_cost(?,?)");
        preparedStatement.setInt(1,id);
        preparedStatement.setFloat(2,newCost);
    }
    public ArrayList<Item> getAllItems() throws SQLException {
        ArrayList<Item> items = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement("call get_all_items_with_amount()");
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            items.add(resultToItem(rs));
        }
        return items;
    }
}
