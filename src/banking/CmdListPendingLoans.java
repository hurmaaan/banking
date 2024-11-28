package banking;

public class CmdListPendingLoans implements Command {

    @Override
    public void execute(String[] cmdParts) {
        Loans.getInstance().listPendingLoans();
    }

}
