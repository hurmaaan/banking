package banking;

public class CmdRejectLoan implements Command {

    @Override
    public void execute(String[] cmdParts) {
        String loanId = cmdParts[0];
        boolean success = Loans.getInstance().rejectLoan(loanId);
        if (!success) {
            System.out.println("Invalid Loan Id");
            return;
        }

        System.out.println("Loan Rejected Successfully");
    }

}
