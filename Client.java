
public class Client implements Role {
    String username;
    private InputHandler scanner = InputHandler.getInstance();

    public Client(String username) {
        this.username = username;
    }

    @Override
    public void displayMenu() {
        while (true) {
            System.out.println("\n--- Client Menu ---");
            System.out.println("1. View Account Details");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer");
            System.out.println("5. Apply for Loan");
            System.out.println("6. Repay Loan");
            System.out.println("7. Logout");
            System.out.print("Choose an option: ");
            int choice = scanner.getNextInt();
            scanner.getNextLine();
            switch (choice) {
                case 1:
                    viewAccountDetails();
                    break;
                case 2:
                    deposit();
                    break;
                case 3:
                    withdraw();
                    break;
                case 4:
                    transfer();
                    break;
                case 5:
                    applyForLoan();
                    break;
                case 6:
                    repayLoan();
                    break;
                case 7:
                    RecordedCommand.reset();
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }

    }

    private void repayLoan() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'repayLoan'");
    }

    private void applyForLoan() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'applyForLoan'");
    }

    private void transfer() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'transfer'");
    }

    private void withdraw() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'withdraw'");
    }

    private void deposit() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deposit'");
    }

    private void viewAccountDetails() {
        new CmdListAccounts().execute(new String[] { username });

    }

    @Override
    public String toString() {
        return "Client";
    }

}
