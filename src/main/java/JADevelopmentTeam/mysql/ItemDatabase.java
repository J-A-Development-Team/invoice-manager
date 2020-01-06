package JADevelopmentTeam.mysql;

import JADevelopmentTeam.Item;
import JADevelopmentTeam.TaxManager;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ItemDatabase extends Database {
    ItemDatabase(String user, String password) {
        super(user, password);
    }
    public static Item resultToItem(ResultSet rs) throws SQLException {
        return new Item(rs.getString("name"),rs.getFloat("cost"), TaxManager.stringToTax(rs.getString("tax")),rs.getInt("item_id"));
    }
}
