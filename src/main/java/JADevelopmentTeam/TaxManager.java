package JADevelopmentTeam;

public class TaxManager {
    public enum taxType {
        o23,
        o8,
        o5,
        o0,
        zw
    }

    public static taxType stringToTax(String s) {
        switch (s) {
            case "o23":
            case "23%":
                return taxType.o23;
            case "o8":
            case "8%":
                return taxType.o8;
            case "o5":
            case "5%":
                return taxType.o5;
            case "o0":
            case "0%":
                return taxType.o0;
            default:
                return taxType.zw;
        }
    }

    public static String taxToString(taxType tax) {
        switch (tax) {
            case o23:
                return "23%";
            case o8:
                return "8%";
            case o5:
                return "5%";
            case o0:
                return "0%";
            case zw:
                return "zw";
            default:
                return "zw";
        }
    }

    public static float taxCalculation(float net, taxType tax) {
        switch (tax) {
            case o23:
                return Math.round(net * 23f)/100f;
            case o8:
                return Math.round(net * 8f)/100f;
            case o5:
                return Math.round(net * 5f)/100f;
            default:
                return 0f;
        }
    }
}
