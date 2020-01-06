package JADevelopmentTeam;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;

public final class Invoice {
    private final int number;
    private final int clientId;
    private final String clientName;
    private final String clientNip;
    private final String clientAddressFirstLine;
    private final String clientAddressSecondLine;
    private final String clientCity;
    private final String clientPostCode;
    private final Date date;
    private final float totalNet;
    private final float totalGross;
    private final ArrayList<InvoiceElement> elements;

    Invoice(int number, Client client, Date date, float totalNet, float totalGross, ArrayList<InvoiceElement> elements) {
        this.number = number;
        this.clientId = client.getId();
        this.clientName = client.getName();
        this.clientNip = client.getNip();
        this.clientAddressFirstLine = client.getAddressFirstLine();
        this.clientAddressSecondLine = client.getAddressSecondLine();
        this.clientCity = client.getCity();
        this.clientPostCode = client.getPostCode();
        this.date = date;
        this.totalNet = totalNet;
        this.totalGross = totalGross;
        this.elements = elements;
    }

    public int getNumber() {
        return number;
    }

    public int getClientId() {
        return clientId;
    }

    public String getClientName() {
        return clientName;
    }

    private String getClientNip() {
        return clientNip;
    }

    private String getClientAddressFirstLine() {
        return clientAddressFirstLine;
    }

    private String getClientAddressSecondLine() {
        return clientAddressSecondLine;
    }

    private String getClientCity() {
        return clientCity;
    }

    private String getClientPostCode() {
        return clientPostCode;
    }

    public Date getDate() {
        return date;
    }

    public float getTotalNet() {
        return totalNet;
    }

    public float getTotalGross() {
        return totalGross;
    }

    public String getElementsAsJson() {
        Gson gsonBuilder = new GsonBuilder().create();
        Type elementsType = new TypeToken<ArrayList<InvoiceElement>>() {
        }.getType();
        return gsonBuilder.toJson(elements, elementsType);
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder("number: " + getNumber() +
                "\n client name: " + getClientName() +
                "\n Address: \n " + getClientAddressFirstLine() + " " + getClientAddressSecondLine() +
                "\n " + getClientPostCode() + " " + getClientCity() +
                "\n NIP: " + getClientNip() +
                "\n " + getDate() +
                "\n Products: " + "\n ");
        for (InvoiceElement element : elements) {
            string.append(element.getItem().getName()).append(" x ").append(element.getQuantity()).append(" net: ").append(element.getSubtotalNet()).append(" gross: ").append(element.getSubtotalGross()).append("\n ");
        }
        string.append("Total Net: ").append(totalNet).append("\n Total Gross: ").append(totalGross);
        return string.toString();

    }
}
