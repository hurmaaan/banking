package banking;

public class CmdSetInterestRates implements Command {

    @Override
    public void execute(String[] cmdParts) {
        Savings.getInstance().setInterestRate(Double.parseDouble(cmdParts[0]));
        Checking.getInstance().setInterestRate(Double.parseDouble(cmdParts[1]));
    }

}
