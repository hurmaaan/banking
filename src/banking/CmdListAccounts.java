package banking;

public class CmdListAccounts implements Command {

    @Override
    public void execute(String[] cmdParts) {
        // cmd parts is username
        User user = UserDatabase.getInstance().findUser(cmdParts[0]);
        Bank.getInstance().listAccounts(user);
    }

}
