package banking;

public interface AccountType {

    void setInterestRate(double rate);

    double calculateMonthlyPayment(int termInMonths, double loanAmount);

}
