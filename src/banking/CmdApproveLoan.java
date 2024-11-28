package banking;

public class CmdApproveLoan implements Command {

    @Override
    public void execute(String[] cmdParts) {
        // cmdParts is loanId
        String loanId = cmdParts[0];
        boolean success = Loans.getInstance().approveLoan(loanId);
        if (!success) {
            System.out.println("Invalid Loan Id");
            return;
        }

        System.out.println("Loan Approved Successfully");

    }

}
