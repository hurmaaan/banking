package banking;

public class Withdrawal extends Transaction {

    public Withdrawal(double amount) {
        super(amount);
    }

    @Override
    public void execute(String[] cmdParts) {
        String accId = cmdParts[0];
        String username = cmdParts[1];
        Account ac = Bank.getInstance().userHasAccount(username, accId);
        if (ac == null) {
            System.out.println("Invalid account id");
            return;
        }
        ac.withdraw(this, Double.parseDouble(super.toString()));
    }

    @Override
    public String toString() {
        return "Withdrew " + super.toString() + ".";
    }

}
