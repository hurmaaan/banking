package banking;

public class CmdApplyLoan implements Command {

    @Override
    public void execute(String[] cmdParts) {
        // 0 is account id 1 is amount, 2 is term//3 is username
        String accId = cmdParts[0];
        double amount = Double.parseDouble(cmdParts[1]);
        int termInMonths = Integer.parseInt(cmdParts[2]);
        String username = cmdParts[3];
        Account ac = Bank.getInstance().userHasAccount(username, accId);
        if (ac == null) {
            System.out.println("Invalid account id");
            return;
        }
        Loans.getInstance().createLoanApplication(ac, termInMonths, amount);
    }

}
