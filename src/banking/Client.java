package banking;

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
            System.out.println("7. List transaction history");
            System.out.println("8. Logout");
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
                    listTransactions();
                case 8:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }

    }

    private void listTransactions() {
        System.out.print("Enter your accountId");
        String accId = scanner.getNextLine();
        new CmdListTransactions().execute(new String[] { username, accId });
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
        System.out.print("Enter your account number: ");
        String accId = scanner.getNextLine();
        System.out.print("Enter other account number: ");
        String accId2 = scanner.getNextLine();
        System.out.print("Enter amount to transfer");
        String amount = scanner.getNextLine();
        new Transfer(Double.parseDouble(amount), true, accId2).execute(new String[] { username, accId });
    }

    private void withdraw() {
        System.out.print("Enter your account number: ");
        String accId = scanner.getNextLine();
        System.out.print("Enter amount to withdraw");
        String amount = scanner.getNextLine();
        new Withdrawal(Double.parseDouble(amount)).execute(new String[] { accId, username });

    }

    private void deposit() {
        System.out.print("Enter your account number: ");
        String accId = scanner.getNextLine();
        System.out.print("Enter amount to deposit");
        String amount = scanner.getNextLine();
        new Deposit(Double.parseDouble(amount)).execute(new String[] { accId, username });

    }

    private void viewAccountDetails() {
        new CmdListAccounts().execute(new String[] { username });

    }

    @Override
    public String toString() {
        return "Client";
    }

}
