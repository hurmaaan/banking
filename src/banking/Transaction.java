package banking;

public abstract class Transaction implements Command {
    private double amount;

    public Transaction(double amount) {
        this.amount = amount;
    }

    @Override

    public String toString() {
        return String.valueOf(amount);
    }
}
