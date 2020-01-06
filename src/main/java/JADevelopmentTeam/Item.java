package JADevelopmentTeam;

public class Item {
    private final String name;
    private final Float netAmount;
    private final TaxManager.taxType taxType;
    private final int id;

    public Item(String name, Float netAmount, TaxManager.taxType taxType,int id) {
        this.name = name;
        this.netAmount = netAmount;
        this.taxType = taxType;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Float getNetAmount() {
        return netAmount;
    }

    public TaxManager.taxType getTaxType() {
        return taxType;
    }
}
