package banking;

import java.util.ArrayList;
import java.util.List;

public class UserDatabase {
    private List<User> users;

    private static UserDatabase instance = null;

    public static UserDatabase getInstance() {
        if (instance == null) {
            instance = new UserDatabase();
        }
        return instance;
    }

    private UserDatabase() {
        users = new ArrayList<>();
        // default accounts
        users.add(new User("admin", "admin123", new Manager("admin")));
        users.add(new User("emp", "manager123", new Employee("emp")));
        users.add(new User("customer", "customer123", new Client("customer")));
    }

    public User findUser(String username) {

        return User.userExists(username, users);
    }

    public void addUser(User u) {
        users.add(u);
    }

    public boolean removeUser(User u) {
        return users.remove(u);
    }

    public static void reset() {
        instance = null;
    }
}