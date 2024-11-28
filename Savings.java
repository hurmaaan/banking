public class Savings implements AccountType {

    private double interestRate;
    private static Savings instance = null;

    private Savings() {

    }

    public static Savings getInstance() {
        if (instance == null) {
            instance = new Savings();

        }
        return instance;
    }

    public void setInteresetRate(double interestRate) {
        this.interestRate = interestRate;
    }

    @Override
    public String toString() {
        return "Savings";
    }
}
