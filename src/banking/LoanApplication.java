package banking;

import java.util.List;

public class LoanApplication {
    private Account account;
    private double outstandingBalance;
    private double monthlyPayment;
    private String loanId;
    private static int LoanId = 2;
    private LoanStatus status;

    public LoanApplication(Account account, double loanAmount) {
        this.account = account;
        this.outstandingBalance = loanAmount;
        this.status = LoanPending.getInstance();
        this.loanId = String.valueOf(LoanId++);

    }

    public void approveLoan() {
        this.status = LoanApproved.getInstance();
    }

    public static LoanApplication createLoanApplication(List<LoanApplication> loanApplications, Account acc,
            int termInMonths, double initialAmount) {
        for (LoanApplication loan : loanApplications) {
            if (acc.toString().equals(loan.account.toString())) {
                System.out.println("This account already has a pending loan. Cannot Apply For a new Loan.");
                return null;
            }
        }
        LoanApplication newApp = new LoanApplication(acc, initialAmount);
        newApp.monthlyPayment = acc.calculateMonthlyPayment(termInMonths, initialAmount);
        System.out.println("Applied Successfuly for a loan.\n Monthly Payment: " + newApp.monthlyPayment + "\nLoan ID: "
                + newApp.loanId);

        return newApp;
    }

    public static void listPendingLoans(List<LoanApplication> pendingLoanApplications) {
        System.out.println("--- Pending Loan Applications ---");
        for (LoanApplication loan : pendingLoanApplications) {
            System.out.println("Loan ID: " + loan.loanId + ", Account ID: " + loan.account.toString() +
                    ", Amount: " + loan.outstandingBalance);
        }
    }

    @Override
    public String toString() {
        return loanId;
    }

    public void rejectLoan() {
        status = LoanRejected.getInstance();
    }

    public void repayLoan(RepayLoan repayLoan, String username, double amount) {
        String accountId = account.toString();
        // makes sure a user can only repay his/her loans
        Account account = Bank.getInstance().userHasAccount(username, accountId);
        if (account == null) {
            System.out.println("Invalid loan id");
            return;
        }

        if (!status.canRepay()) {
            return;
        }

        if (amount < monthlyPayment) {
            System.out.println("Amount cannot be less than monthly payment");
            return;
        }
        outstandingBalance -= account.repayLoan(repayLoan, amount, outstandingBalance);
        if (outstandingBalance <= 0) {
            status = LoanPaid.getInstance();
            System.out.println("Loan Fully Repaid!");
        }
    }

    public static void reset() {
        LoanId = 2;
    }
}
