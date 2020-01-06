package JADevelopmentTeam;

public final class ClientBuilder {
    private int id;
    private String name;
    private String addressFirstLine;
    private String addressSecondLine;
    private String city;
    private String postCode;
    private String nip;

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddressFirstLine(String addressFirstLine) {
        this.addressFirstLine = addressFirstLine;
    }

    public void setAddressSecondLine(String addressSecondLine) {
        this.addressSecondLine = addressSecondLine;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public Client getClient() {
        return new Client(id, name, addressFirstLine, addressSecondLine, city, postCode, nip);
    }
}
