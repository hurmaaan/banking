package bankingTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import banking.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {

    private User user1;
    private User user2;
   
    private Account account1;
    private Account account2;
    
    private Savings savings = Savings.getInstance();
    private Checking checking = Checking.getInstance();

    @Test
    void test1() {
    	assertEquals(1, 1);
    }
    @BeforeEach
    void setUp() throws Exception {
    	setOutput();
    	Client client1 = new Client("user1");
    	Client client2 = new Client("user2");
        user1 = new User("user1", "pwd1", client1);
        user2 = new User("user1", "pwd1", client2);
        account1 = new Account(1000.0, savings, user1); // id = 109
        account2 = new Account(2000.0, checking, user2); // id = 110
    }

    @Test
    void testListAccounts_AccountsExist() throws Exception {
        List<Account> accounts = new ArrayList<>();
        accounts.add(account1);
        accounts.add(account2);

        // Test listing for user1
        account1.listAccounts(accounts, user1);
        
        String output = getOutput();
        String[] lines = output.split("\n");
      
        assertEquals("Balance: " + 1000.0, lines[1]);
        assertEquals("Type: " + savings, lines[2]);
        assertEquals("------", lines[3]);
    }

    @Test
    void testListAccounts_NoAccountsExist() throws Exception {
        List<Account> accounts = new ArrayList<>();

        // No accounts for user1
        account1.listAccounts(accounts, user1);
        
        assertEquals("This user has no accounts\n", getOutput());
    }

    @Test
    void testUserHasAccounts_True() throws Exception {
        List<Account> accounts = new ArrayList<>();
        accounts.add(account1);

        assertTrue(Account.userHasAccounts(accounts, user1));
    }

    @Test
    void testUserHasAccounts_False() throws Exception {
        List<Account> accounts = new ArrayList<>();
        accounts.add(account2);

        assertFalse(Account.userHasAccounts(accounts, user1));
    }

    @Test
    void testPrintDetails() throws Exception {
        account1.printDetails();
        String output = getOutput();
        String[] lines = output.split("\n");
        
        assertEquals("Balance: " + 1000.0, lines[1]);
        assertEquals("Type: " + savings, lines[2]);
        assertEquals("------", lines[3]);
        // Since printDetails only prints, we verify by running it (mocking `System.out` for assertions is optional)
    }

    @Test
    void testToString() throws Exception {
        assertNotNull(account2.toString());
    }

    @Test
    void testCloseAccount_WithNonZeroBalance() throws Exception {
        account1.close(account1);
        assertEquals("Cannot close account with remaining balance. Please withdraw first!\n", getOutput());
    }
    
    @Test
    void testCloseAccount_WithZeroBalance() {
        Account zeroBalanceAccount = new Account(0.0, savings, user1);
        zeroBalanceAccount.close(zeroBalanceAccount);
        assertEquals("Account Closed Successfully!\n", getOutput());
    }
    
    @Test
    void testAccountBelongsToUser_Found() {
        List<Account> accounts = new ArrayList<>();
        accounts.add(account1);

        Account result = Account.accountBelongsToUser(accounts, "user1", account1.toString());
        assertNotNull(result);
        assertEquals(account1, result);
    }

    @Test
    void testAccountBelongsToUser_NotFound() {
        List<Account> accounts = new ArrayList<>();
        accounts.add(account2);

        Account result = Account.accountBelongsToUser(accounts, "user1", account1.toString());
        assertNull(result);
    }
    
    @Test
    void testDeposit() {
    	Client client = new Client("testUser");
    	User testUser = new User("testUser", "pwd", client);
        Account account = new Account(100.0, checking, testUser);
        Deposit deposit = new Deposit(50.0);
        account.deposit(deposit, 50.0);
        
        String output = getOutput();
        String[] lines = output.split("\n");
        
        assertEquals("Deposit Successful!", lines[0]);
        assertEquals("New Balance: 150.0", lines[1]);
    }

    @Test
    void testWithdraw_SufficientBalance() {
    	Withdrawal withdrawal = new Withdrawal(200.0);
        account1.withdraw(withdrawal, 200.0);
        
        String output = getOutput();
        String[] lines = output.split("\n");
        
        assertEquals("Withdrawal Accepted!", lines[0]);
        assertEquals("New Balance: 800.0", lines[1]);
    }

    @Test
    void testWithdraw_InsufficientBalance() {
    	Withdrawal withdrawal = new Withdrawal(200.0);
        account1.withdraw(withdrawal, 2000.0);
        
        String output = getOutput();
        String[] lines = output.split("\n");
        
        assertEquals("Not Enough Balance", lines[0]);
    }
    
    @Test
    void testTransfer_Successful() {
        List<Account> accounts = new ArrayList<>();
        accounts.add(account1); // sender
        accounts.add(account2); // receiver
        
        Transfer transfer = new Transfer(500.0, true, account2.toString());
        Account.transfer(transfer, accounts, account2.toString(), account1, 500.0);
        account1.listTransactions();
        account2.listTransactions();
        
        String output = getOutput();
        String[] lines = output.split("\n");
        
        assertEquals("Transfer Successfull!", lines[0]);
        assertEquals("Transferred 500.0 to " + account2.toString(), lines[5]);
        assertEquals("Received 500.0 from " + account1.toString(), lines[10]);
    }

    @Test
    void testTransfer_InvalidReceiver() {
    	 List<Account> accounts = new ArrayList<>();
         accounts.add(account1); // sender
         
         Transfer transfer = new Transfer(100.0, true, account2.toString());
         Account.transfer(transfer, accounts, account2.toString(), account1, 100.0);
         account1.printDetails();
         
         String output = getOutput();
         String[] lines = output.split("\n");
         
         assertEquals("Invalid receiver account", lines[0]);
         assertEquals("Balance: 1000.0", lines[2]);
    }

    @Test
    void testTransfer_InsufficientBalance() {
    	List<Account> accounts = new ArrayList<>();
        accounts.add(account1); // sender
        accounts.add(account2); // receiver
        
        Transfer transfer = new Transfer(2000.0, true, account2.toString());
        Account.transfer(transfer, accounts, account2.toString(), account1, 2000.0);
        account1.printDetails();
        
        String output = getOutput();
        String[] lines = output.split("\n");
        
        assertEquals("Insufficient Balance", lines[0]);
        assertEquals("Balance: 1000.0", lines[2]);
    }
//
//    @Test
//    void testListTransactions() {
//        Account account = new Account(300.0, new Checking(), mockUser);
//        account.deposit(mockDeposit, 50.0);
//        account.withdraw(mockWithdrawal, 100.0);
//
//        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
//        System.setOut(new PrintStream(outContent));
//        account.listTransactions();
//
//        String output = outContent.toString();
//        assertTrue(output.contains("Account ID:"));
//        assertTrue(output.contains("Balance:"));
//        assertTrue(output.contains(mockDeposit.toString()));
//        assertTrue(output.contains(mockWithdrawal.toString()));
//        System.setOut(System.out); // Reset the standard output
//    }
    
    
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