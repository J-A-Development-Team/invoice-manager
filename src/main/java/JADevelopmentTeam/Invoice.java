package JADevelopmentTeam;

import java.util.ArrayList;
import java.util.Date;

public class Invoice {
    private final int invoiceId;
    private final ArrayList<InvoiceElement> elements;
    private final String clientName;
    private final String clientNIP;
    private final String clientPostCode;
    private final String clientStreetAndNumber;
    private final String clientCity;
    private final Float fullGross;
    private final Float fullNet;
    private final Date date;

    Invoice(final int invoiceId, final ArrayList<InvoiceElement> elements, String clientName,
            String clientNIP, String clientPostCode, String clientStreetAndNumber,
            String clientCity, float fullNet, float fullGross, Date date) {
        this.invoiceId = invoiceId;
        this.elements = elements;
        this.clientName = clientName;
        this.clientNIP = clientNIP;
        this.clientPostCode = clientPostCode;
        this.clientStreetAndNumber = clientStreetAndNumber;
        this.clientCity = clientCity;
        this.fullNet = fullNet;
        this.fullGross = fullGross;
        this.date = date;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("Faktura VAT Nr " + invoiceId + ",z dnia " + date + "\n" +
                "Sprzedawca: " + Company.name + "\n" +
                "       " + Company.streetAndNumber + "\n" +
                "       " + Company.postcode + " " + Company.city + "\n" +
                "NIP    " + Company.NIP + "\n" +
                "Nabywca\n" +
                clientName + "\n" +
                clientStreetAndNumber + ", " + clientPostCode + " " + clientCity + "\n" +
                "NIP    " + clientNIP + "\n" +
                "Nazwa towaru lub usługi\n");
        for (InvoiceElement element :
                elements) {
            s.append(element.getItem().getName());
            s.append(" Ilość ");
            s.append(String.format("%,.2f", element.getQuantity()));
            s.append(" netto ");
            s.append(String.format("%,.2f", element.getItem().getNetAmount()));
            s.append(" stawka ");
            s.append(TaxManager.taxToString(element.getItem().getTaxType()));
            s.append(" kwota ");
            s.append(String.format("%,.2f", TaxManager.taxCalculation(element.netCalculation(), element.getItem().getTaxType())));
            s.append(" Wartość z podatkiem ");
            s.append(String.format("%,.2f", element.grossCalculation()));
            s.append("\n");
        }
        s.append("\n    Do zapłaty      netto: ");
        s.append(String.format("%,.2f", fullNet));
        s.append("  brutto: ");
        s.append(String.format("%,.2f", fullGross));
        return s.toString();
    }

    public int getInvoiceId() {
        return invoiceId;
    }

    public String getClientNIP() {
        return clientNIP;
    }

    public ArrayList<InvoiceElement> getElements() {
        return elements;
    }

    public Float getFullGross() {
        return fullGross;
    }

    public Float getFullNet() {
        return fullNet;
    }

    public Date getDate() {
        return date;
    }
}
