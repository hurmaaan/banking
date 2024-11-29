package banking;

public interface AccountType {

    void setInteresetRate(double rate);

    double calculateMonthlyPayment(int termInMonths, double loanAmount);

    // LoanApplication createLoanApplication(Account acc, int termInMonths, double
    // loanAmount);
}
