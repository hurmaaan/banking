package banking;

public class CmdListTransactions implements Command {

    @Override
    public void execute(String[] cmdParts) {
        // 0 is username 1 is accid
        String username = cmdParts[0];
        String accId = cmdParts[1];
        Bank.getInstance().listTransactions(username, accId);
    }

}
