package banking;

public class Checking implements AccountType {
    private double interestRate = 0.2;
    private static Checking instance = null;

    private Checking() {

    }

    public static Checking getInstance() {
        if (instance == null) {
            instance = new Checking();

        }
        return instance;
    }

    @Override

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    @Override
    public double calculateMonthlyPayment(int termInMonths, double loanAmount) {
        double monthlyInterestRate = interestRate / 12;

        // Calculate monthly payment using the formula
        double monthlyPayment = loanAmount *
                (monthlyInterestRate * Math.pow(1 + monthlyInterestRate, termInMonths)) /
                (Math.pow(1 + monthlyInterestRate, termInMonths) - 1);

        return monthlyPayment;
    }

    @Override
    public String toString() {
        return "Checking";
    }
}
