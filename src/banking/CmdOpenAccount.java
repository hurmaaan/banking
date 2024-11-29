package banking;

public class CmdOpenAccount extends RecordedCommand {

    private Account account;

    @Override
    public void execute(String[] cmdParts) {
        // cmd parts are accountBalance,accountType, and username
        double balance = Double.parseDouble(cmdParts[0]);
        AccountType type = cmdParts[1].equalsIgnoreCase("Savings") ? Savings.getInstance() : Checking.getInstance();
        User user = UserDatabase.getInstance().findUser(cmdParts[2]);
        account = new Account(balance, type, user);
        Bank.getInstance().addAccount(account);
        System.out.println("Account Opened Successfully!");
        account.printDetails();
        addUndoCommand(this);
        clearRedoList();
    }

    @Override
    public void undoMe() {
        Bank.getInstance().removeAccount(account);
        System.out.println("Undo Successful. Closed Bank Account " + account);
        super.addRedoCommand(this);

    }

    @Override
    public void redoMe() {
        Bank.getInstance().addAccount(account);
        System.out.println("Redo Successful. ReOpened Bank Account " + account);

        super.addUndoCommand(this);
    }

}
