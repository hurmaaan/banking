package banking;

public class LoanRepaymentAdjustment extends Transaction {

    public LoanRepaymentAdjustment(double amount) {
        super(amount);
    }

    @Override
    public void execute(String[] cmdParts) {
        // do nothing
    }

    @Override
    public String toString() {
        return "Loan repayment Adjustment: " + super.toString();
    }

}
