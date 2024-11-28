package banking;

public class LoanApplication {
    int termInMonths;
    Account account;
    // double loanAmount;
    double outstandingBalance;
    double monthlyPayment;
    // status

    public LoanApplication(Account account, int termInMonths, double loanAmount) {
        this.account = account;
        this.termInMonths = termInMonths;
        this.outstandingBalance = loanAmount;
    }

    public double calculateMonthlyPayments() {
        return 0;
    }
}
