package banking;

import java.util.List;
import java.util.ArrayList;

public class Account {
    private User user;
    private double balance;
    private AccountType type;
    private String accountId;
    private List<Transaction> transactions;
    private static int firstAccountId = 109;

    public Account(double balance, AccountType type, User user) {
        this.balance = balance;
        this.type = type;
        accountId = String.valueOf(firstAccountId++);
        this.user = user;
        transactions = new ArrayList<>();
    }

    public static void listAccounts(List<Account> accounts, User user) {
        if (userHasAccounts(accounts, user)) {
            for (Account acc : accounts) {
                if (user == acc.user) {
                    acc.printDetails();
                }
            }
        } else {
            System.out.println("This user has no accounts");
        }
    }

    public static boolean userHasAccounts(List<Account> accounts, User user) {
        for (Account acc : accounts) {
            if (user == acc.user) {
                return true;
            }
        }
        return false;
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

    public void close() {
        if (balance != 0) {
            System.out.println("Cannot close account with remaining balance. Please withdraw first!");
            return;
        }
        new CmdCloseAccount().execute(new String[] { accountId });
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
        System.out.println("Deposit Successful!\n New Balance: " + balance);
    }

    public void withdraw(Withdrawal withdrawal, double amount) {
        if (amount > balance) {
            System.out.println("Not Enough Balance");
            return;
        }
        balance -= amount;
        transactions.add(withdrawal);
        System.out.println("Withdrawal Accepted!\n New Balance: " + balance);
    }

    public static void transfer(Transfer transfer, Account receiverAccount,
            Account senderAccount,
            double amount) {

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

    public double calculateMonthlyPayment(int termInMonths, double loanAmount) {
        return type.calculateMonthlyPayment(termInMonths, loanAmount);
    }

    public double repayLoan(RepayLoan repayLoan, double amount, double outstandingBalance) {
        if (amount > balance) {
            System.out.println("Insufficient Balance.");
            return 0;
        }

        balance -= amount;
        transactions.add(repayLoan);
        System.out.println("Repayment Made!");
        if (amount > outstandingBalance) {
            balance += amount - outstandingBalance; // add the difference when client repays more than loanamount
            Transaction t = new LoanRepaymentAdjustment(amount - outstandingBalance);

            t.execute(new String[] { repayLoan.toString().split(" ")[4] });
            transactions.add(t);
        }
        return amount;

    }

    public static void reset() {
        firstAccountId = 109;
    }
}
