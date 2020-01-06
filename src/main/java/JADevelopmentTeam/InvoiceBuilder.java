package JADevelopmentTeam;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;

public class InvoiceBuilder {
    private ArrayList<InvoiceElement> elements = new ArrayList<>();
    private Client client;
    private Date date;
    private float totalNet;
    private float totalGross;
    private int number;

    public static ArrayList<InvoiceElement> convertInvoiceElementsFromJson(String json) {
        Gson gsonBuilder = new GsonBuilder().create();
        Type elementsType = new TypeToken<ArrayList<InvoiceElement>>() {
        }.getType();
        return gsonBuilder.fromJson(json, elementsType);
    }

    public void addInvoiceElement(final InvoiceElement elementToAdd) {
        elements.add(elementToAdd);
    }

    public Invoice getInvoice() {
        if (totalGross == 0.0f) totalGross = calcTotalGross();
        if (totalNet == 0.0f) totalNet = calcTotalNet();
        return new Invoice(number, client, date, totalNet, totalGross, elements);
    }

    private float calcTotalNet() {
        float totalNet = 0;
        for (InvoiceElement element : elements) {
            totalNet += element.getSubtotalNet();
        }
        return totalNet;
    }

    private float calcTotalGross() {
        float totalGross = 0;
        for (InvoiceElement element : elements) {
            totalGross += element.getSubtotalGross();
        }
        return totalGross;
    }

    public void setElements(ArrayList<InvoiceElement> elements) {
        this.elements = elements;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setTotalNet(float totalNet) {
        this.totalNet = totalNet;
    }

    public void setTotalGross(float totalGross) {
        this.totalGross = totalGross;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
