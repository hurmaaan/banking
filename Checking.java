public class Checking implements AccountType {
    private double interestRate;
    private static Checking instance = null;

    private Checking() {

    }

    public static Checking getInstance() {
        if (instance == null) {
            instance = new Checking();

        }
        return instance;
    }

    public void setInteresetRate(double interestRate) {
        this.interestRate = interestRate;
    }

    @Override
    public String toString() {
        return "Checking";
    }
}
