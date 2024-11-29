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

public class EmployeeTest {

    private Employee employee;
    private Bank bank;

    private PrintStream originalOut;
    private ByteArrayOutputStream outputStream;
    private InputStream originalIn;

    @BeforeEach
    public void setUp() {
        // Redirect System.out to capture output
        originalOut = System.out;
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // Redirect System.in to simulate user input
        originalIn = System.in;
    }

    @AfterEach
    public void tearDown() {
        // Restore System.out and System.in
        System.setOut(originalOut);
        System.setIn(originalIn);
    }

    @Test
    public void testOpenAccount_UserDoesNotExist() {
        // Simulate user input
        String simulatedInput = "nonexistentUser\n100.0\nSavings\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        // Create an instance of Employee
        employee = new Employee();
        bank = Bank.getInstance();

        // Call the method
        employee.openAccount();

        // Capture the output
        String output = outputStream.toString();
        String[] lines = output.split("\n");

        // Assert the expected output
        assertEquals("Enter user ID for the account: ", lines[0]);
        assertEquals("Enter initial deposit amount: ", lines[1]);
        assertEquals("Enter account type (Savings/Checking): ", lines[2]);
        assertEquals("User does not exist!", lines[3]);
    }

    // Additional tests can be added here
}
