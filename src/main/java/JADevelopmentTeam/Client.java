package JADevelopmentTeam;

public class Client {
    public final int id;
    public final String name;
    public final String NIP;
    public final String city;
    public final String streetAndNumber;
    public final String postcode;

    public Client(int id, String name, String NIP, String city, String streetAndNumber, String postcode) {
        this.id = id;
        this.name = name;
        this.NIP = NIP;
        this.city = city;
        this.streetAndNumber = streetAndNumber;
        this.postcode = postcode;
    }

    @Override
    public String toString() {
        return name;
    }
}
