package banking;

import java.util.List;
import java.util.ArrayList;

public class Account {
    private User user;
    private double balance;
    private AccountType type;
    private String accountId = null;
    private List<Transaction> transactions;
    private static int firstAccountId = 109;

    public Account(double balance, AccountType type, User user) {
        this.balance = balance;
        this.type = type;
        accountId = String.valueOf(firstAccountId++);
        this.user = user;
        transactions = new ArrayList<>();
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

    public static Account accountBelongsToUser(List<Account> accounts, String username, String accId) {
        for (Account a : accounts) {
            if (a.accountId.equals(accId) && a.user.toString().equals(username)) {
                return a;
            }
        }
        return null;
    }

    public void deposit(Deposit deposit, Double amount) {
        this.balance += amount;
        transactions.add(deposit);
        System.out.println("Deposit Successful!\nNew Balance: " + balance);
    }

    public void withdraw(Withdrawal withdrawal, double amount) {
        if (amount > balance) {
            System.out.println("Not Enough Balance");
            return;
        }
        balance -= amount;
        transactions.add(withdrawal);
        System.out.println("Withdrawal Accepted!\nNew Balance: " + balance);
    }

    public static void transfer(Transfer transfer, List<Account> accounts, String receiverAccountId,
            Account senderAccount,
            double amount) {
        Account receiverAccount = null;
        for (Account a : accounts) {
            if (a.accountId.equals(receiverAccountId)) {
                receiverAccount = a;
            }
        }

        if (receiverAccount == null) {
            System.out.println("Invalid receiver account");
            return;
        }
        if (senderAccount.balance < amount) {
            System.out.println("Insufficient Balance");
            return;
        }
        senderAccount.balance -= amount;
        receiverAccount.balance += amount;
        System.out.println("Transfer Successfull!");
        senderAccount.transactions.add(transfer);
        receiverAccount.transactions.add(new Transfer(amount, false, senderAccount.accountId));
    }

    public void listTransactions() {
        printDetails();
        for (Transaction t : transactions) {
            System.out.println(t.toString());
        }
    }
}
