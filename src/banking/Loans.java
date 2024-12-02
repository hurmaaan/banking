package banking;

import java.util.ArrayList;
import java.util.List;

public class Loans {

    private static Loans instance = null;
    private List<LoanApplication> pendingLoanApplications;
    private List<LoanApplication> processedLoanApplications;

    public void createLoanApplication(Account acc, int termInMonths, double initialAmount) {

        // check if that account already has an outstanding loan
        LoanApplication loanApplication = LoanApplication.createLoanApplication(pendingLoanApplications, acc,
                termInMonths,
                initialAmount);

        if (loanApplication != null) {
            pendingLoanApplications.add(loanApplication);

        }

    }

    public void listPendingLoans() {

        LoanApplication.listPendingLoans(pendingLoanApplications);

    }

    private Loans() {
        pendingLoanApplications = new ArrayList<>();
        processedLoanApplications = new ArrayList<>();
    }

    public static Loans getInstance() {
        if (instance == null) {
            instance = new Loans();
        }
        return instance;
    }

    public boolean approveLoan(String loanId) {
        LoanApplication app = isLoanPending(loanId);
        if (app == null) {
            return false;
        }
        pendingLoanApplications.remove(app);
        processedLoanApplications.add(app);
        app.approveLoan();
        return true;
    }

    public boolean rejectLoan(String loanId) {
        LoanApplication app = isLoanPending(loanId);
        if (app == null) {
            return false;
        }
        pendingLoanApplications.remove(app);
        processedLoanApplications.add(app);
        app.rejectLoan();
        return true;
    }

    private LoanApplication isLoanPending(String loanId) {
        for (LoanApplication app : pendingLoanApplications) {
            if (loanId.equals(app.toString())) {
                return app;
            }
        }
        return null;
    }

    public void repayLoan(RepayLoan repayLoan, String loanId, String username, double amount) {
        for (LoanApplication app : processedLoanApplications) {
            if (app.toString().equals(loanId)) {
                app.repayLoan(repayLoan, username, amount);
                return;
            }
        }
        System.out.println("Either loan Id is invalid or loan has not been approved yet.");
    }

    public static void reset() {
        instance = null;
    }

}
