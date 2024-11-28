public class CmdSetInterestRates implements Command {

    @Override
    public void execute(String[] cmdParts) {
        Savings.getInstance().setInteresetRate(Double.parseDouble(cmdParts[0]));
        Checking.getInstance().setInteresetRate(Double.parseDouble(cmdParts[1]));
    }

}
