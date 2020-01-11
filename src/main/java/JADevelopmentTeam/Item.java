package JADevelopmentTeam;

public class Item {
    private final String name;
    private final Float netAmount;
    private final TaxManager.taxType taxType;
    private final String description;
    private final int id;
    private final float availableAmount;
    public Item(String name, Float netAmount, TaxManager.taxType taxType, String description, int id, float availableAmount) {
        this.name = name;
        this.netAmount = netAmount;
        this.taxType = taxType;
        this.description = description;
        this.id = id;
        this.availableAmount = availableAmount;
    }

    public String getDescription() {
        return description;
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

    public float getAvailableAmount() {
        return availableAmount;
    }

    @Override
    public String toString() {
        return name+" "+netAmount+" "+TaxManager.taxToString(taxType)+" "+ availableAmount;
    }
}