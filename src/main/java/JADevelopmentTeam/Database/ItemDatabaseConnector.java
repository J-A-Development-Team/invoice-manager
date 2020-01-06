package JADevelopmentTeam.Database;



import JADevelopmentTeam.Item;
import JADevelopmentTeam.ItemBuilder;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemDatabaseConnector {
    private MySQLDatabase database;
    private ResultSet resultSet;

    public ItemDatabaseConnector(MySQLDatabase database) {
        this.database = database;
    }

    public Item getItemById(int id) throws SQLException {
        resultSet = database.itemStatement.executeQuery("select * from invoiceManager.items where id='" + id + "';");
        resultSet.next();
        return writeItem(resultSet);
    }

    public Item getItemByName(String name) throws SQLException {
        resultSet = database.itemStatement.executeQuery("select * from invoiceManager.items where name='" + name + "';");
        resultSet.next();
        return writeItem(resultSet);
    }

    public ArrayList<Item> getAllItems() throws SQLException {
        resultSet = database.itemStatement.executeQuery("select * from invoiceManager.items;");
        return writeItems(resultSet);

    }

    public void addItem(Item  item) throws SQLException {
        String query = " insert into items (name, price, taxType)"
                + " values (?, ?, ?)";
        PreparedStatement preparedStatement = database.connection.prepareStatement(query);
        preparedStatement.setString(1, item.getName());
        preparedStatement.setFloat(2, item.getPrice());
        preparedStatement.setString(3, item.getTaxType().toString());
        preparedStatement.execute();
    }

    private Item writeItem(ResultSet resultSet) throws SQLException {
        ItemBuilder itemBuilder = new ItemBuilder();
        itemBuilder.setId(resultSet.getInt("id"));
        itemBuilder.setName(resultSet.getString("name"));
        itemBuilder.setPrice(resultSet.getFloat("price"));
        itemBuilder.setTaxTypeFromString(resultSet.getString("taxType"));
        return itemBuilder.getItem();
    }

    private ArrayList<Item> writeItems(ResultSet resultSet) throws SQLException {
        ArrayList<Item> items = new ArrayList<>();
        while (resultSet.next()) {
            items.add(writeItem(resultSet));
        }
        return items;
    }
}
