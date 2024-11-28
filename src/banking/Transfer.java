package banking;

public class Transfer extends Transaction {
    private String otherAccountId;
    private boolean isSender;

    public Transfer(double amount, boolean isSender, String otherAccountId) {
        super(amount);
        this.isSender = isSender;
        this.otherAccountId = otherAccountId;
    }

    @Override
    public void execute(String[] cmdParts) {
        // String accId = cmdParts[0];
        String username = cmdParts[0];
        String thisAccountId = cmdParts[1];

        Bank.getInstance().transfer(this, thisAccountId, username, otherAccountId,
                Double.parseDouble(super.toString()));
    }

    @Override
    public String toString() {
        return isSender ? "Transferred " + super.toString() + " to " + otherAccountId
                : "Received " + super.toString() + " from " + otherAccountId;
    }

}
