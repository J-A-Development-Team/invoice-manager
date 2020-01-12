package JADevelopmentTeam;

public class InvoiceElement {
    private final Item item;
    private final float quantity;

    public InvoiceElement(Item item, float quantity) {
        this.item = item;
        this.quantity = quantity;

    }

    public float grossCalculation() {
        return netCalculation() + TaxManager.taxCalculation(netCalculation(), item.getTaxType());
    }

    public Item getItem() {
        return item;
    }

    public float getQuantity() {
        return quantity;
    }

    public float netCalculation() {
        return quantity * item.getNetAmount();
    }

    @Override
    public String toString() {
        return item.getName()+" amount: "+quantity+" net: "+ netCalculation()+" gross: "+grossCalculation();
    }
}
