package bankingTest;

import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.*;
import banking.*;

import static org.junit.jupiter.api.Assertions.*;

class BankTest {
    private Bank bank;
    private User user;
    private Account account;
    
    private Savings savings = Savings.getInstance();
    private Checking checking = Checking.getInstance();

    @BeforeEach
    void setUp() throws Exception {
    	setOutput();
        bank = Bank.getInstance(); // Singleton instance
        user = new User("testUser", "password", new Client("testUser")); // Sample User
        UserDatabase.getInstance().addUser(user); // Add user to the database
        account = new Account(1000.0, checking, user); // Sample Account
        bank.addAccount(account); // Add account to the bank
    }

    @AfterEach
    void tearDown() {
        // Clear bank and user database
        UserDatabase.getInstance().removeUser(user);
        bank.removeAccount(account);
    }

    @Test
    void testAddAccount() {
        Account newAccount = new Account(2000.0, savings, user);
        bank.addAccount(newAccount);
        assertTrue(bank.hasAccount(user));
    }

    @Test
    void testRemoveAccountByObject() {
        bank.removeAccount(account);
        assertFalse(bank.hasAccount(user));
    }

    @Test
    void testListAccounts() {
        bank.listAccounts(user);
        
        String output = getOutput();
        assertTrue(output.contains("Account ID:"));
        assertTrue(output.contains("Balance: 1000.0"));
    }

    @Test
    void testHasAccount() {
        assertTrue(bank.hasAccount(user));
    }

    @Test
    void testCloseAccountWithValidAccount() {
        account.deposit(new Deposit(-1000.0), -1000.0); // Simulate withdrawal to balance 0
        bank.closeAccount(account.toString());
        assertFalse(bank.hasAccount(user));
    }
    
    @Test
    void testCloseAccountWithInvalidAccount() {
        bank.closeAccount("adsfasdfasd");
        String output = getOutput();
        assertTrue(output.contains("Account Not Found"));
    }

    @Test
    void testCloseAccountWithNonZeroBalance() {
        bank.closeAccount(account.toString());

        String output = getOutput();
        assertTrue(output.contains("Cannot close account with remaining balance. Please withdraw first!"));
    }

    @Test
    void testUserHasAccount() {
        Account result = bank.userHasAccount("testUser", account.toString());
        assertEquals(account, result);
    }

    @Test
    void testUserHasAccountInvalidAccount() {
        Account result = bank.userHasAccount("testUser", "invalidId");
        assertNull(result);
    }

    @Test
    void testTransferValid() {
        Account receiverAccount = new Account(500.0, checking, user);
        bank.addAccount(receiverAccount);

        Transfer transfer = new Transfer(200.0, true, account.toString());
        bank.transfer(transfer, account.toString(), "testUser", receiverAccount.toString(), 200.0);
        account.printDetails();
        receiverAccount.printDetails();
        
        String output = getOutput();
        
        assertTrue(output.contains("Transfer Successful"));
        assertTrue(output.contains("Balance: 800.0"));
        assertTrue(output.contains("Balance: 700.0"));
    }
    
    @Test
    void testTransferInvalidSender() {
        Transfer transfer = new Transfer(200.0, true, account.toString());
        bank.transfer(transfer, "invalidId", "testUser", account.toString(), 200.0);

        String output = getOutput();
        assertTrue(output.contains("Inavlid Sender Account ID"));
    }

    @Test
    void testTransferInvalidReceiver() {
        Transfer transfer = new Transfer(200.0, true, account.toString());
        bank.transfer(transfer, account.toString(), "testUser", "invalidId", 200.0);

        String output = getOutput();
        assertTrue(output.contains("Invalid receiver account"));
    }

    @Test
    void testTransferInsufficientFunds() {
        Account receiverAccount = new Account(500.0, checking, user);
        bank.addAccount(receiverAccount);

        Transfer transfer = new Transfer(2000.0, true, account.toString()); // Amount greater than balance
        bank.transfer(transfer, account.toString(), "testUser", receiverAccount.toString(), 2000.0);

        String output = getOutput();
        assertTrue(output.contains("Insufficient Balance"));
    }

    @Test
    void testListTransactions() {
        account.deposit(new Deposit(500.0), 500.0);

        bank.listTransactions("testUser", account.toString());

        String output = getOutput();
        assertTrue(output.contains("Deposit Successful"));
    }

    @Test
    void testListTransactionsInvalidAccount() {
        bank.listTransactions("testUser", "invalidId");

        String output = getOutput();
        assertTrue(output.contains("Invalid account Id"));
    }
    
    /**************************************
	 * Note: The following is to handle output reading
	 ***************************************/
    PrintStream oldPrintStream;
	ByteArrayOutputStream bos;

	private void setOutput() throws Exception {
		oldPrintStream = System.out;
		bos = new ByteArrayOutputStream();
		System.setOut(new PrintStream(bos));
	}

	private String getOutput() { // throws Exception
		System.setOut(oldPrintStream);
		return bos.toString();
	}
}
