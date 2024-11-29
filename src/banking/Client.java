package banking;

public class Client implements Role {
    private String username;
    private InputHandler scanner = InputHandler.getInstance();

    public Client(String username) {
        this.username = username;
    }

    @Override
    public void displayMenu() {
    	System.out.println("IN MENU");
        while (true) {
            System.out.println("\n--- Client Menu ---");
            System.out.println("1. View Account Details");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer");
            System.out.println("5. Apply for Loan");
            System.out.println("6. Make Monthly Loan Payment");
            System.out.println("7. List transaction history");
            System.out.println("8. Logout");
            System.out.print("Choose an option: ");
            int choice = scanner.getNextInt();
            scanner.getNextLine();
            switch (choice) {
                case 1:
                	System.out.println("CHOSE 1");
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
                    break;
                case 8:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }

    }

    public void listTransactions() {
        System.out.print("Enter your accountId");
        String accId = scanner.getNextLine();
        new CmdListTransactions().execute(new String[] { username, accId });
    }

    private void repayLoan() {
        System.out.print("Enter Loan Id: ");
        String loanId = scanner.getNextLine();
        System.out.print("Enter amount to repay: ");
        Double amount = scanner.getNextDouble();
        new RepayLoan(amount).execute(new String[] { loanId, username });
    }

    private void applyForLoan() {
        System.out.print("Enter your account ID: ");
        String accountId = scanner.getNextLine();
        System.out.print("Enter loan amount: ");
        String amount = scanner.getNextLine();
        System.out.print("Enter loan term (in months): ");
        String termInMonths = scanner.getNextLine();
        new CmdApplyLoan().execute(new String[] { accountId, amount, termInMonths, username });
    }

    public void transfer() {
        System.out.print("Enter your account number: ");
        String accId = scanner.getNextLine();
        System.out.print("Enter other account number: ");
        String accId2 = scanner.getNextLine();
        System.out.print("Enter amount to transfer");
        String amount = scanner.getNextLine();
        if (accId.equals(accId2)) {
            System.out.println("Cannot transfer to the same account!");
            return;
        }
        new Transfer(Double.parseDouble(amount), true, accId2).execute(new String[] { username, accId });
    }

    public void withdraw() {
        System.out.print("Enter your account number: ");
        String accId = scanner.getNextLine();
        System.out.print("Enter amount to withdraw");
        String amount = scanner.getNextLine();
        new Withdrawal(Double.parseDouble(amount)).execute(new String[] { accId, username });

    }

    public void deposit() {
        System.out.print("Enter your account number: ");
        String accId = scanner.getNextLine();
        System.out.print("Enter amount to deposit");
        String amount = scanner.getNextLine();
        new Deposit(Double.parseDouble(amount)).execute(new String[] { accId, username });

    }

    public void viewAccountDetails() {
        new CmdListAccounts().execute(new String[] { username });

    }

    @Override
    public String toString() {
        return "Client";
    }

}
