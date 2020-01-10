package JADevelopmentTeam.mysql;

import JADevelopmentTeam.Item;
import JADevelopmentTeam.TaxManager;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ItemDatabase extends Database {
    ItemDatabase(String user, String password) {
        super(user, password);
    }
    public static Item resultToItem(ResultSet rs) throws SQLException {
        return new Item(rs.getString("name"),rs.getFloat("cost"), TaxManager.stringToTax(rs.getString("tax")),rs.getInt("item_id"), 1);
    }
    public void add_item(String name,TaxManager.taxType taxType,String description,Float cost,float availableAmount) throws SQLException {
        preparedStatement = connection.prepareStatement("call add_item (?,?,?,?,?)");
        preparedStatement.setString(1,name);
        preparedStatement.setString(3,description);
        preparedStatement.setString(2,TaxManager.taxToString(taxType));
        preparedStatement.setFloat(4,cost);
        preparedStatement.setFloat(5,availableAmount);
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
}
