package banking;

import java.util.ArrayList;
import java.util.List;

public class Loans {

    private static Loans instance = null;
    private List<LoanApplication> pendingLoanApplications;
    private List<LoanApplication> approvedLoanApplications;
    private List<LoanApplication> rejectedLoanApplications;

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
        approvedLoanApplications = new ArrayList<>();
        rejectedLoanApplications = new ArrayList<>();
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
        approvedLoanApplications.add(app);
        app.approveLoan();
        return true;
    }

    public boolean rejectLoan(String loanId) {
        LoanApplication app = isLoanPending(loanId);
        if (app == null) {
            return false;
        }
        pendingLoanApplications.remove(app);
        rejectedLoanApplications.add(app);
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

}
