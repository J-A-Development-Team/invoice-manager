package JADevelopmentTeam;

public final class ItemBuilder {
    private int id;
    private float price;
    private String name;
    private TaxManager.TaxType taxType;

    public Item getItem() {
        return new Item(id, price, name, taxType);
    }

    public void setTaxTypeFromString(String taxType) {
        switch (taxType) {
            case "o5":
                this.taxType = TaxManager.TaxType.o5;
                break;
            case "o7":
                this.taxType = TaxManager.TaxType.o7;
                break;
            case "oz":
                this.taxType = TaxManager.TaxType.oz;
                break;
            default:
                this.taxType = TaxManager.TaxType.st;
                break;

        }
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTaxType(TaxManager.TaxType taxType) {
        this.taxType = taxType;
    }

}
