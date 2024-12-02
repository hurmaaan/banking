package banking;

public class Savings implements AccountType {

    private double interestRate = 0.1;
    private static Savings instance = null;

    private Savings() {

    }

    public static Savings getInstance() {
        if (instance == null) {
            instance = new Savings();

        }
        return instance;
    }

    @Override
    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    @Override
    public String toString() {
        return "Savings";
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

}
