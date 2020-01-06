package JADevelopmentTeam;

public class Item {
    private final int id;
    private final float price;
    private final String name;
    private final TaxManager.TaxType taxType;

    public Item(int id, float price, String name, TaxManager.TaxType taxType) {
        this.id = id;
        this.price = price;
        this.name = name;
        this.taxType = taxType;
    }

    public int getId() {
        return id;
    }

    public float getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public TaxManager.TaxType getTaxType() {
        return taxType;
    }
}
