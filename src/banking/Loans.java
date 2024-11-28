package banking;

import java.util.ArrayList;
import java.util.List;

public class Loans {

    private static Loans instance = null;
    private List<LoanApplication> loanApplications;

    public void createLoanApplication(Account acc, int termInMonths, double initialAmount) {

        // check if that account already has an outstanding loan
        LoanApplication loanApplication = LoanApplication.createLoanApplication(loanApplications, acc, termInMonths,
                initialAmount);

        if (loanApplication != null) {
            loanApplications.add(loanApplication);

        }

    }

    private Loans() {
        loanApplications = new ArrayList<>();
    }

    public static Loans getInstance() {
        if (instance == null) {
            instance = new Loans();
        }
        return instance;
    }

}
