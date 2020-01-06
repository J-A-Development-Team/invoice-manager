package JADevelopmentTeam;

import java.util.ArrayList;
import java.util.Date;

public class InvoiceBuilder {
    private final ArrayList<InvoiceElement> elements;
    public InvoiceBuilder(){
        elements = new ArrayList<>();
    }

    public void addElement(InvoiceElement invoiceElement){
        elements.add(invoiceElement);
    }

    public Invoice createNewInvoice(Client client, Date date, int id){
        float net=0;
        float gross=0;
        for (InvoiceElement element:
             elements) {
            net+=element.netCalculation();
            gross+=element.grossCalculation();
        }
        return new Invoice(id,elements,client.name,client.NIP,client.postcode,client.streetAndNumber,client.city,net,gross,date);
    }

}