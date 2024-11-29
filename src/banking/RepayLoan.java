package banking;

public class RepayLoan extends Transaction {

    private String loanId;

    public RepayLoan(double amount) {
        super(amount);
    }

    @Override
    public void execute(String[] cmdParts) {
        loanId = cmdParts[0];
        String username = cmdParts[1];
        Loans.getInstance().repayLoan(this, loanId, username, Double.parseDouble(super.toString()));

    }

    @Override
    public String toString() {
        return "Repaid " + super.toString() + " for loan " + loanId;
    }
}
