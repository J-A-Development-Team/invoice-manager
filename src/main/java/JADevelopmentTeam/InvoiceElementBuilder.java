package JADevelopmentTeam;

public class InvoiceElementBuilder {
    private Item item;
    private int quantity;

    public InvoiceElement getElement() {
        return new InvoiceElement(item, quantity);
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
