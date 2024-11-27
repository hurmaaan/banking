import java.util.ArrayList;
import java.util.List;

public class Bank {
    private static Bank instance = null;

    private List<Account> accounts;
    UserDatabase userDatabase;
    Authenticator authenticator;

    private Bank() {
        // accounts = new ArrayList<>();
        userDatabase = UserDatabase.getInstance();
        authenticator = new Authenticator(userDatabase);
        accounts = new ArrayList<>();
    }

    public static Bank getInstance() {
        if (instance == null) {
            instance = new Bank();
        }
        return instance;
    }

    public void start() {
        while (true) {
            User user = authenticator.authenticate();
            if (user != null) {
                user.menu();
            }
        }

    }

    public void addAccount(Account a) {
        accounts.add(a);
    }

    public void removeAccount(Account a) {
        accounts.remove(a);
    }

    public void listAccounts(User user) {
        Account.listAccounts(accounts, user);
    }

    private Account getAccountById(String id) {
        for (Account account : accounts) {
            if (account.toString().equals(id)) {
                return account;
            }
        }
        return null;
    }

    public boolean hasAccount(User user) {
        return Account.userHasAccounts(accounts, user);
    }

    public void closeAccount(String accountId) {
        Account acc = getAccountById(accountId);
        if (acc == null) {
            System.out.println("Account Not Found");
            return;
        }

        Account.close(acc);
    }

    public Account removeAccount(String string) {
        Account a = getAccountById(string);
        accounts.remove(a);
        return a;
    }
}
