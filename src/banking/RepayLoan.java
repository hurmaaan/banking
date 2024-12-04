package banking;

public class RepayLoan extends Transaction {

    private String loanId;

    public RepayLoan(double amount, String loanId) {
        super(amount);
        this.loanId = loanId;
    }

    @Override
    public void execute(String[] cmdParts) {

        String username = cmdParts[0];
        Loans.getInstance().repayLoan(this, loanId, username, Double.parseDouble(super.toString()));

    }

    @Override
    public String toString() {
        return "Repaid " + super.toString() + " for loan " + loanId;
    }
}
