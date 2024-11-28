package bankingTest;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import banking.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class ClientTest {
    private Client client;
    private ByteArrayOutputStream outputStream;
    
    private final InputStream originalSystemIn = System.in;
    private ByteArrayInputStream testIn;

    @BeforeEach
    void setUp() throws Exception {
    	setOutput();
        client = new Client("testUser");
//        outputStream = new ByteArrayOutputStream();
//        System.setOut(new PrintStream(outputStream));
    }
    
    @AfterEach
    public void restoreSystemIn() {
        // Restore System.in after each test
        System.setIn(originalSystemIn);
    }

    @Test
    void testToString() {
        assertEquals("Client", client.toString());
    }

    @Test
    void testViewAccountDetails() {
    	Client client1 = new Client("user1");
        User user1 = new User("user1", "pwd1", client1);
        Account account1 = new Account(1000.0, Savings.getInstance(), user1);
    			
    	UserDatabase userDatabase = UserDatabase.getInstance();
        userDatabase.addUser(user1);
        
        User found_user = userDatabase.findUser("test_customer");
        System.out.println("User found: " + user1);  // Debug line
        
        client.viewAccountDetails();
        
        String output = getOutput();
        assertTrue(output.contains("Balance: 100.0"));
        assertTrue(output.contains("------"));
    	
    }

//    @Test
//    void testDeposit() {
//        String input = "12345\n100.50\n"; // Account ID and deposit amount
//        System.setIn(new ByteArrayInputStream(input.getBytes()));
//
//        client.deposit();
//
//        String output = outputStream.toString();
//        assertTrue(output.contains("Enter your account number:"));
//        assertTrue(output.contains("Enter amount to deposit"));
//        // Check the simulated deposit message
//        assertTrue(output.contains("Deposit executed for account"));
//    }
//
//    @Test
//    void testWithdraw() {
//        String input = "12345\n50.00\n"; // Account ID and withdrawal amount
//        System.setIn(new ByteArrayInputStream(input.getBytes()));
//
//        client.withdraw();
//
//        String output = outputStream.toString();
//        assertTrue(output.contains("Enter your account number:"));
//        assertTrue(output.contains("Enter amount to withdraw"));
//        // Check the simulated withdrawal message
//        assertTrue(output.contains("Withdraw"));
//    }
//
//    @Test
//    void testTransfer() {
//        String input = "12345\n54321\n200.00\n"; // Sender, receiver, and transfer amount
//        System.setIn(new ByteArrayInputStream(input.getBytes()));
//
//        client.transfer();
//
//        String output = outputStream.toString();
//        assertTrue(output.contains("Enter your account number:"));
//        assertTrue(output.contains("Enter other account number:"));
//        assertTrue(output.contains("Enter amount to transfer"));
//        // Check the simulated transfer message
//        assertTrue(output.contains("Transfer executed"));
//    }
//
//    @Test
//    void testListTransactions() {
//        String input = "12345\n"; // Account ID
//        System.setIn(new ByteArrayInputStream(input.getBytes()));
//
//        client.listTransactions();
//
//        String output = outputStream.toString();
//        assertTrue(output.contains("Enter your accountId"));
//        // Check the simulated list transactions output
//        assertTrue(output.contains("Transaction history for account"));
    
		//  InputStream sysInBackup = System.in; // backup System.in to restore it later
		//  ByteArrayInputStream in = new ByteArrayInputStream("8\n".getBytes());
		//  System.setIn(in);
//		  
//		  Client client = new Client("testUser");
//		
//		  // Call the method that contains the menu
//		  client.listTransactions();
//		  String output = getOutput();
//		  assertTrue(output.contains("Enter your accountId"));
		  
		  
		//  System.setIn(sysInBackup);
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
