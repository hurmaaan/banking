package banking;

public class Deposit extends Transaction {

    public Deposit(double amount) {
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
        ac.deposit(this, Double.parseDouble(super.toString()));

    }

    @Override

    public String toString() {
        return "Deposited " + super.toString();
    }

}
