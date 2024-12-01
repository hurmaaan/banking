package banking;

public class Authenticator {
    private UserDatabase userDatabase;

    public Authenticator(UserDatabase userDatabase) {
        this.userDatabase = userDatabase;
    }

    public User authenticate() {
        InputHandler scanner = InputHandler.getInstance();
        System.out.print("Enter username: ");
        String username = scanner.getNextLine();
        System.out.print("Enter password: ");
        String password = scanner.getNextLine();
        User user = userDatabase.findUser(username);
        if (user != null) {
            if (user.isPasswordCorrect(password)) {
                System.out.println("Login successful! Welcome, " + user.getRole() + ".");
                return user;
            } else {
                System.out.println("Login failed! Invalid password.");
                return null;
            }
        } else {
            System.out.println("User does not exist.");
            return null;
        }

    }
}