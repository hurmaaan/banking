package bankingTest;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import banking.*;

import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.io.PrintStream;
import java.io.InputStream;

public class DepositTest {

    private Deposit deposit;
    private Bank bank;
    private Account account;
    private Savings savings;

    private PrintStream originalOut;
    private ByteArrayOutputStream outputStream;
    private InputStream originalIn;

    @BeforeEach
    public void setUp() {
        // Redirect System.out to capture output
        originalOut = System.out;
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // Redirect System.in to simulate input
        originalIn = System.in;

        bank = Bank.getInstance();
        savings = Savings.getInstance();

        User user = new User("client", "12345", new Client("client"));

        account = new Account(100.0, savings, user); // accId = "109"
        bank.addAccount(account);

        deposit = new Deposit(100.0);
    }

    @AfterEach
    public void tearDown() {
        // Restore original System.out and System.in
        System.setOut(originalOut);
        System.setIn(originalIn);
    }

    @Test
    public void testExecute_ValidAccount() {
        // Simulate command parts for a valid account
        String[] cmdParts = {"109", "client"};
        deposit.execute(cmdParts);

        // Capture and process the output
        String output = outputStream.toString();
        String[] lines = output.split("\n");

        // Assertions
        assertEquals("Deposit Successful!", lines[0]);
        assertEquals(" New Balance: " + 200.0, lines[1]);
    }

    @Test
    public void testExecute_InvalidAccount() {
        // Simulate command parts for an invalid account
        String[] cmdParts = {"190", "client"};
        deposit.execute(cmdParts);

        // Capture and process the output
        String output = outputStream.toString();
        String[] lines = output.split("\n");

        // Assertions
        assertEquals("Invalid account id", lines[0]);
    }

    @Test
    public void testToString() {
        // Test the toString method of Deposit
        assertEquals("Deposited " + 100.0, deposit.toString());
    }
}