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
	private PrintStream originalOut;
    private ByteArrayOutputStream outputStream;
    private InputStream originalIn;
    
    private Client client;
    private User user;
    private Account account;
    private UserDatabase userDatabase;
    
    
    @BeforeEach
    private void setUp() {
    	InputHandler.resetInstance();
    	
//    	client = new Client("user1");
//        user = new User("user1", "pwd1", client);
//        account = new Account(1000.0, Savings.getInstance(), user);
    	
    	// Redirect System.out to capture output
        originalOut = System.out;
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // Redirect System.in to simulate user input
        originalIn = System.in;
    }

    @Test
    void testToString() {
    	client = new Client("user1");
        assertEquals("Client", client.toString());
    }

    @Test
    void testViewAccountDetails() {
    	String simulatedInput = "1\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        
    	client = new Client("user1");
        user = new User("user1", "pwd1", client);
        userDatabase = UserDatabase.getInstance();
        userDatabase.addUser(user);
        
        client.viewAccountDetails();
        
        String output = outputStream.toString();
        
        System.out.println(output);
//        assertTrue(output.contains("Balance: 1000.0"));
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
  
}
