package banking;

public interface AccountType {

    void setInteresetRate(double rate);

    double calculateMonthlyPayment(int termInMonths, double loanAmount);

}
