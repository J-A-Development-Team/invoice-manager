package JADevelopmentTeam;

public class Item {
    private final String name;
    private final Float netAmount;
    private final TaxManager.taxType taxType;
    private final int id;
    private final int availableAmount;
    public Item(String name, Float netAmount, TaxManager.taxType taxType, int id, int availableAmount) {
        this.name = name;
        this.netAmount = netAmount;
        this.taxType = taxType;
        this.id = id;
        this.availableAmount = availableAmount;
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

    public int getAvailableAmount() {
        return availableAmount;
    }

    @Override
    public String toString() {
        return name+" "+netAmount+" "+taxType+" "+ availableAmount;
    }
}