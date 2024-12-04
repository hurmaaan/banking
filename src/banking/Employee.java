package banking;

public class Employee extends Client {
    private InputHandler scanner = InputHandler.getInstance();

    public Employee(String username) {
        super(username);
    }

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
            System.out.println("10. Manage your account");
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
                case 10:
                    super.displayMenu();
                    return;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }

    private void registerClient() {
        System.out.print("Enter new username:");
        String username = scanner.getNextLine();
        User u = UserDatabase.getInstance().findUser(username);
        if (u != null) {
            System.out.println("Username already exists!");
            return;
        }
        boolean passwordMatching = false;
        String password1 = "", password2;
        while (!passwordMatching) {
            System.out.print("Enter Password");
            password1 = scanner.getNextLine();
            System.out.print("Re-enter Password");
            password2 = scanner.getNextLine();
            if (!password1.equals(password2)) {
                System.out.println("Passwords do not match. Try again.");
            } else {
                passwordMatching = true;
            }
        }

        new CmdRegisterUser().execute(new String[] { username, password1, "client" });

    }

    private void listAllAccounts() {
        System.out.print("Enter username:");
        String username = scanner.getNextLine();
        User u = UserDatabase.getInstance().findUser(username);
        if (u == null) {
            System.out.println("Invalid username");
            return;
        }
        System.out.println("Accounts belonging to " + username);
        new CmdListAccounts().execute(new String[] { username });

    }

    private void processLoanApplication() {
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

    private void listPendingLoans() {
        new CmdListPendingLoans().execute(null);
    }

    private void closeAccount() {
        System.out.print("Enter Account Id to close:");

        String accountId = scanner.getNextLine();
        Bank.getInstance().closeAccount(accountId);
    }

    private void openAccount() {

        System.out.print("Enter user ID for the account: ");
        String username = scanner.getNextLine();
        System.out.print("Enter initial deposit amount: ");
        String initialDeposit = scanner.getNextLine();
        System.out.print("Enter account type (Savings/Checking): ");
        String type = scanner.getNextLine();

        User user = UserDatabase.getInstance().findUser(username);
        if (user == null) {
            System.out.println("User does not exist!");
            return;
        }
        try {
            if (Double.parseDouble(initialDeposit) <= 0) {
                System.out.println("Invalid amount entered.\n Aborting Transaction...");
                return;
            }
            String[] cmdParts = new String[] { initialDeposit, type, username };
            new CmdOpenAccount().execute(cmdParts);

        } catch (NumberFormatException e) {
            System.out.println("Invalid amount entered.");

        }

    }

    @Override
    public String toString() {
        return "Employee";
    }
}
