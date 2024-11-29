package banking;

public class CmdCloseAccount extends RecordedCommand {

    private Account account;

    @Override
    public void execute(String[] cmdParts) {
        // cmd[0] is account ID
        account = Bank.getInstance().removeAccount(cmdParts[0]);
        System.out.println("Account Closed Successfully!");
        addUndoCommand(this);
        clearRedoList();
    }

    @Override
    public void undoMe() {
        Bank.getInstance().addAccount(account);
        System.out.println("Undo Successful. Reopened Bank Account " + account);
        super.addRedoCommand(this);
    }

    @Override
    public void redoMe() {
        Bank.getInstance().removeAccount(account);
        System.out.println("Redo Successful. Reclosed Bank Account " + account);
        super.addUndoCommand(this);
    }

}
