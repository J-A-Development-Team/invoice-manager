package JADevelopmentTeam;

public class User {
    Type type;
    int id;
    String name;
    public enum Type{
        admin,manager,worker
    }
    String password;

    public User(Type type, int id, String name, String password) {
        this.type = type;
        this.id = id;
        this.name = name;
        this.password = password;
    }
    public User(){};

    public static Type stringToType(String type){
        switch (type){
            case "admin":
                return Type.admin;
            case "manager":
                return Type.manager;
            default:
                return Type.worker;
        }
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return name + " | "+ type;
    }
}
