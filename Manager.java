
public class Manager implements Role {
    private InputHandler scanner = InputHandler.getInstance();

    @Override
    public void displayMenu() {
        while (true) {
            System.out.println("\n--- Manager Menu ---");
            System.out.println("1. Set Interest Rates");
            System.out.println("2. Manage Employees");
            System.out.println("3. Undo Last Command.");
            System.out.println("4. Redo Last Command");
            System.out.println("5. Logout");
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
                    System.out.println("Logging out...");
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
        if (username == null) {
            System.out.println("Invalid username.");
            return;
        }
        if (Bank.getInstance().hasAccount(u)) {
            System.out.println("Employee has account(s). Please close them first.");
            return;
        }

        new CmdRemoveUser().execute(new String[] { username });
    }

    private void setInterestRates() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setInterestRates'");
    }

    @Override
    public String toString() {
        return "Manager";
    }

}
