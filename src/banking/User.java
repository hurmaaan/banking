package banking;

import java.util.List;

public class User {
    private String username;
    private Role role;
    private String password;

    public User(String username, String password, Role role) {
        this.username = username;
        this.role = role;
        this.password = password;
    }

    public void menu() {
        role.displayMenu();
    }

    public String getRole() {
        return role.toString();
    }

    public static User userExists(String username, List<User> users) {
        for (User user : users) {
            if (user.username.equals(username)) {
                return user;
            }
        }
        return null;
    }

    public boolean isPasswordCorrect(String pass) {
        return pass.equals(password);
    }

    @Override
    public String toString() {
        return username;
    }
}
