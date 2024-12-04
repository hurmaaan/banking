package banking;

public class Manager extends Client {
    private InputHandler scanner;

    public Manager(String username) {
        super(username);
        scanner = InputHandler.getInstance();

    }

    @Override
    public void displayMenu() {
        while (true) {
            System.out.println("\n--- Manager Menu ---");
            System.out.println("1. Set Interest Rates");
            System.out.println("2. Manage Employees");
            System.out.println("3. Undo Last Command.");
            System.out.println("4. Redo Last Command");
            System.out.println("5. Logout");
            System.out.println("6. Manage your accounts.");

            System.out.print("Choose an option: ");
            int choice = scanner.getNextInt();
            scanner.getNextLine();

            switch (choice) {
                case 1:
                    setInterestRates();
                    break;
                case 2:
                    manageEmployees();
                    break;
                case 3:
                    RecordedCommand.undoOneCommand();
                    break;
                case 4:
                    RecordedCommand.redoOneCommand();
                    break;
                case 5:
                    RecordedCommand.reset();
                    System.out.println("\nLogging out...");
                    return;
                case 6:
                    super.displayMenu();
                    return;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }

    private void manageEmployees() {
        System.out.println("Employee Management:");
        System.out.println("1. Add Employee");
        System.out.println("2. Remove Employee");
        System.out.print("Choose an option: ");
        int choice = scanner.getNextInt();
        scanner.getNextLine();
        switch (choice) {
            case 1:
                addEmployee();
                break;
            case 2:
                removeEmployee();
                break;
            default:
                System.out.println("Invalid option. Returning to menu.");
        }
    }

    private void addEmployee() {
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

        new CmdRegisterUser().execute(new String[] { username, password1, "employee" });

    }

    private void removeEmployee() {
        System.out.print("Enter username of employee to remove: ");
        String username = scanner.getNextLine();
        User u = UserDatabase.getInstance().findUser(username);
        if (u == null) {
            System.out.println("Invalid username.");
            return;
        }

        new CmdRemoveUser().execute(new String[] { username });
    }

    private void setInterestRates() {
        System.out.println("This command cannot be undone!");
        System.out.println("Set Interest Rates for Account Types:");
        System.out.print("Enter interest rate for Savings Account (e.g., 0.03 for 3%): ");
        String savingsRate = scanner.getNextLine();
        System.out.print("Enter interest rate for Checking Account (e.g., 0.01 for 1%): ");
        String checkingRate = scanner.getNextLine();

        new CmdSetInterestRates().execute(new String[] { savingsRate, checkingRate });

    }

    @Override
    public String toString() {
        return "Manager";
    }

}
