package banking;

public class Employee implements Role {
    private InputHandler scanner = InputHandler.getInstance();

    @Override
    public void displayMenu() {
        while (true) {

            System.out.println("\n--- Employee Menu ---");
            System.out.println("1. Open Account");
            System.out.println("2. Close Account");
            System.out.println("3. List Pending Loan Applications"); // New option
            System.out.println("4. Process Loan Applications");
            System.out.println("5. List accounts");
            System.out.println("6. Register new Client");
            System.out.println("7. Undo Last Action");
            System.out.println("8. Redo Last Action");
            System.out.println("9. Logout");
            System.out.print("Choose an option: ");
            int choice = scanner.getNextInt();
            scanner.getNextLine();

            switch (choice) {
                case 1:
                    openAccount();
                    break;
                case 2:
                    closeAccount();
                    break;
                case 3:
                    listPendingLoans(); // Call to list pending loans
                    break;
                case 4:
                    processLoanApplication();
                    break;
                case 5:
                    listAllAccounts();
                    break;
                case 6:
                    registerClient();
                    break;
                case 7:
                    RecordedCommand.undoOneCommand();
                    break;
                case 8:
                    RecordedCommand.redoOneCommand();
                    break;
                case 9:
                    RecordedCommand.reset();
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }

    public void registerClient() {
        System.out.println("Enter new username:");
        String username = scanner.getNextLine();
        User u = UserDatabase.getInstance().findUser(username);
        if (u != null) {
            System.out.println("Username already exists!");
            return;
        }
        boolean passwordMatching = false;
        String password1 = "", password2="";
        while (!passwordMatching) {
            System.out.println("Enter Password");
            password1 = scanner.getNextLine();
            System.out.println("Re-enter Password");
            password2 = scanner.getNextLine();
            if (!password1.equals(password2)) {
                System.out.println("Passwords do not match. Try again.");
            } else {
                passwordMatching = true;
            }
        }

        new CmdRegisterUser().execute(new String[] { username, password1, "client" });

    }

    public void listAllAccounts() {
        System.out.println("Enter username:");
        String username = scanner.getNextLine();
        UserDatabase.getInstance().findUser(username);
        if (username == null) {
            System.out.println("Invalid username");
        }
        System.out.println("Accounts belonging to " + username);
        new CmdListAccounts().execute(new String[] { username });

    }

    public void processLoanApplication() {
        System.out.print("WARNING!!! This action cannot be undone!\nEnter loan application ID: ");
        String loanId = scanner.getNextLine();

        System.out.println("1. Approve Loan");
        System.out.println("2. Reject Loan");
        System.out.print("Choose an option: ");
        int choice = scanner.getNextInt();
        scanner.getNextLine();
        switch (choice) {
            case 1:
                new CmdApproveLoan().execute(new String[] { loanId });
                break;
            case 2:

                new CmdRejectLoan().execute(new String[] { loanId });
                break;
            default:
                System.out.println("Invalid option.");
        }
    }

    public void listPendingLoans() {
        new CmdListPendingLoans().execute(null);
    }

    public void closeAccount() {
        System.out.println("Enter Account Id to close:");

        String accountId = scanner.getNextLine();
        Bank.getInstance().closeAccount(accountId);
    }

    public void openAccount() {

        System.out.println("Enter user ID for the account: ");
        String username = scanner.getNextLine();
        System.out.println("Enter initial deposit amount: ");
        String initialDeposit = scanner.getNextLine();
        System.out.println("Enter account type (Savings/Checking): ");
        String type = scanner.getNextLine();

        User user = UserDatabase.getInstance().findUser(username);
        if (user == null) {
            System.out.println("User does not exist!");
            return;
        }
        // user.openAccount(initialDeposit, type);
        String[] cmdParts = new String[] { initialDeposit, type, username };
        new CmdOpenAccount().execute(cmdParts);

    }

    @Override
    public String toString() {
        return "Employee";
    }
}
