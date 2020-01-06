package JADevelopmentTeam;

public abstract class TaxManager {
    public enum TaxType {
        o5(0.05f), o7(0.07f), oz(0.0f), st(0.23f);
        public final Float v;

        TaxType(float v) {
            this.v = v;
        }
    }
}
