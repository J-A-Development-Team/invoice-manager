package JADevelopmentTeam;

public class InvoiceElement {
    private final Item item;
    private final int quantity;
    private final float subtotalGross;
    private final float subtotalNet;

    InvoiceElement(Item item, int quantity) {
        this.item = item;
        this.quantity = quantity;
        subtotalNet = item.getPrice() * quantity;
        float tax = item.getPrice() * quantity * item.getTaxType().v;
        subtotalGross = item.getPrice() * quantity + tax;

    }

    public float getSubtotalNet() {
        return subtotalNet;
    }

    public float getSubtotalGross() {
        return subtotalGross;
    }


    public Item getItem() {
        return item;
    }

    public int getQuantity() {
        return quantity;
    }
}
