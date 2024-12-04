package banking;

public class LoanRepaymentAdjustment extends Transaction {

    String loanId;

    public LoanRepaymentAdjustment(double amount) {
        super(amount);
    }

    @Override
    public void execute(String[] cmdParts) {
        loanId = cmdParts[0];
    }

    @Override
    public String toString() {
        return "Loan repayment Adjustment: " + super.toString() + " from loanId: " + loanId;
    }

}
