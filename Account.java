import java.util.List;

public class Account {
    private User user;
    private double balance;
    private AccountType type;
    private String accountId = null;

    private static int firstAccountId = 109;

    public Account(double balance, AccountType type, User user) {
        this.balance = balance;
        this.type = type;
        accountId = String.valueOf(firstAccountId++);
        this.user = user;
    }

    public Account(double balance, AccountType type, String accountId) {
        this.balance = balance;
        this.type = type;
        this.accountId = accountId;
    }

    public static void listAccounts(List<Account> accounts, User user) {
        boolean accountsExist = false;
        for (Account acc : accounts) {
            if (user == acc.user) {
                accountsExist = true;
                System.out.println("Account ID: " + acc.accountId);
                System.out.println("Balance: " + acc.balance);
                System.out.println("Type: " + acc.type);
                System.out.println("------");
            }
        }

        if (!accountsExist) {
            System.out.println("This user has no accounts");
        }
    }

    public static boolean userHasAccounts(List<Account> accounts, User user) {
        boolean accountsExist = false;
        for (Account acc : accounts) {
            if (user == acc.user) {
                accountsExist = true;
                break;
            }
        }
        return accountsExist;
    }

    public void printDetails() {
        System.out.println("Account ID: " + accountId);
        System.out.println("Balance: " + balance);
        System.out.println("Type: " + type);
        System.out.println("------");
    }

    @Override
    public String toString() {
        return accountId;
    }

    public static void close(Account acc) {
        if (acc.balance != 0) {
            System.out.println("Cannot close account with remaining balance. Please withdraw first!");
            return;
        }
        new CmdCloseAccount().execute(new String[] { acc.accountId });
    }
}
