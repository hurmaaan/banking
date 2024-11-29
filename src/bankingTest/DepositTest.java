package bankingTest;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import banking.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;


public class DepositTest {

    private Deposit deposit;
    private Bank bank;
    private Account account;
    private Savings savings;

    @BeforeEach
    public void setUp() throws Exception{
    	setOutput();
        bank = Bank.getInstance();
        savings = Savings.getInstance();
        
        User user = new User("client", "12345", new Client("client"));
        
        account = new Account(100.0, savings, user); // accId = "109"
        bank.addAccount(account);

        deposit = new Deposit(100.0);
    }

    @Test
    public void testExecute_ValidAccount() {
        String[] cmdParts = {"109", "client"};
        deposit.execute(cmdParts);
        
        String output = getOutput();
        String[] lines = output.split("\n");
      
        assertEquals("Deposit Successful!", lines[0]);
        assertEquals("New Balance: " + 200.0, lines[1]);
    }
    
    @Test
    public void testExecute_InvalidAccount() {
        String[] cmdParts = {"190", "client"};
        deposit.execute(cmdParts);
        
        String output = getOutput();
        String[] lines = output.split("\n");
      
        assertEquals("Invalid account id", lines[0]);
    }
    
    @Test
    public void testToString() {      
        assertEquals("Deposited " + 100.0, deposit.toString());
    }
    
    /**************************************
   	 * Note: The following is to handle output reading
   	 ***************************************/
    private PrintStream oldPrintStream;
   	private ByteArrayOutputStream bos;

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
