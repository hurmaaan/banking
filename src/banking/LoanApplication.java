package banking;

import java.util.List;

public class LoanApplication {
    private int termInMonths;
    private Account account;
    private double outstandingBalance;
    private double monthlyPayment;

    private LoanStatus status;

    public LoanApplication(Account account, int termInMonths, double loanAmount) {
        this.account = account;
        this.termInMonths = termInMonths;
        this.outstandingBalance = loanAmount;
        this.status = new LoanPending();

    }

    public void repayLoan(double amount) {

        outstandingBalance -= amount;
        if (outstandingBalance <= 0) {
            status = new LoanPaid();
        }
    }

    public void approveLoan() {
        this.status = new LoanApproved();
    }

    public static LoanApplication createLoanApplication(List<LoanApplication> loanApplications, Account acc,
            int termInMonths, double initialAmount) {
        for (LoanApplication loan : loanApplications) {
            if (loan.outstandingBalance > 0 && acc.toString().equals(loan.account.toString())) {
                System.out.println("This account already has an outstanding loan. Cannot Apply For a new Loan. ");
                return null;
            }
        }
        System.out.println("Applied Successfuly for a loan.");
        LoanApplication newApp = new LoanApplication(acc, termInMonths, initialAmount);
        newApp.monthlyPayment = acc.calculateMonthlyPayment(termInMonths, initialAmount);
        return newApp;
    }

}
