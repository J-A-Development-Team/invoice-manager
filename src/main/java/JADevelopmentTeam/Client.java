package JADevelopmentTeam;

public class Client {
    private final int id;
    private final String name;
    private final String addressFirstLine;
    private final String addressSecondLine;
    private final String city;
    private final String postCode;
    private final String nip;

    Client(final int id, final String name,
           final String addressFirstLine, final String addressSecondLine,
           final String city, final String postCode, final String nip) {
        this.id = id;
        this.name = name;
        this.addressFirstLine = addressFirstLine;
        this.addressSecondLine = addressSecondLine;
        this.city = city;
        this.postCode = postCode;
        this.nip = nip;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddressFirstLine() {
        return addressFirstLine;
    }

    public String getAddressSecondLine() {
        return addressSecondLine;
    }

    public String getCity() {
        return city;
    }

    public String getPostCode() {
        return postCode;
    }

    public String getNip() {
        return nip;
    }
}
