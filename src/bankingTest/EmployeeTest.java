package bankingTest;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import banking.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class EmployeeTest {

    private Employee employee;

    private PrintStream originalOut;
    private ByteArrayOutputStream outputStream;

    @BeforeEach
    public void setUp() {
        // Redirect System.out to capture output
        originalOut = System.out;
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // Reset InputHandler singleton and prepare for input injection
        InputHandler.resetInstance();
        employee = new Employee();
    }

    @AfterEach
    public void tearDown() {
        // Restore System.out
        System.setOut(originalOut);
    }

    private void setInput(String simulatedInput) {
        InputHandler.getInstance().setScanner(new Scanner(new ByteArrayInputStream(simulatedInput.getBytes())));
    }

    @Test
    public void testRegisterClient_UsernameAlreadyExists() {
        // Simulate user input
        String simulatedInput = "existingUser\npassword1\npassword1\n";
        setInput(simulatedInput);

        // Add an existing user to the UserDatabase
        UserDatabase.getInstance().addUser(new User("existingUser", "existingPassword", new Client("existingUser")));

        // Call the method
        employee.registerClient();

        // Capture the output
        String output = outputStream.toString();
        String[] lines = output.split("\n");

        // Assert the expected output
        assertEquals("Enter new username:", lines[0]);
        assertEquals("Username already exists!", lines[1]);
    }

    @Test
    public void testRegisterClient_Success() {
        // Simulate user input
        String simulatedInput = "newUser\npassword1\npassword1\n";
        setInput(simulatedInput);

        // Call the method
        employee.registerClient();

        // Capture the output
        String output = outputStream.toString();
        String[] lines = output.split("\n");

        // Assert the expected output
        assertEquals("Enter new username:", lines[0]);
        assertEquals("Enter Password", lines[1]);
        assertEquals("Re-enter Password", lines[2]);

        // Verify the user was added
        User newUser = UserDatabase.getInstance().findUser("newUser");
        assertNotNull(newUser);
        assertEquals("newUser", newUser.toString());
    }

    @Test
    public void testOpenAccount_UserDoesNotExist() {
        // Simulate user input
        String simulatedInput = "nonexistentUser\n100.0\nSavings\n";
        setInput(simulatedInput);

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

    @Test
    public void testOpenAccount_Success() {
        // Simulate user input
        String simulatedInput = "existingUser\n1000\nSavings\n";
        setInput(simulatedInput);

        // Add an existing user to the UserDatabase
        UserDatabase.getInstance().addUser(new User("existingUser", "password", new Client("existingUser")));

        // Call the method
        employee.openAccount();

        // Capture the output
        String output = outputStream.toString();
        String[] lines = output.split("\n");

        // Assert the expected output
        assertEquals("Enter user ID for the account: ", lines[0]);
        assertEquals("Enter initial deposit amount: ", lines[1]);
        assertEquals("Enter account type (Savings/Checking): ", lines[2]);
    }

    @Test
    public void testCloseAccount_Success() {
        // Simulate user input
        String simulatedInput = "12345\n";
        setInput(simulatedInput);

        // Add an account to the Bank
        Bank bank = Bank.getInstance();
        Account account = new Account(100.0, Savings.getInstance(), new User("user1", "pass1", new Client("user1")));
        bank.addAccount(account);

        // Call the method
        employee.closeAccount();

        // Capture the output
        String output = outputStream.toString();
        String[] lines = output.split("\n");

        // Assert the expected output
        assertEquals("Enter Account Id to close:", lines[0]);
    }

    @Test
    public void testListAllAccounts_UserDoesNotExist() {
        // Simulate user input
        String simulatedInput = "nonexistentUser\n";
        setInput(simulatedInput);

        // Call the method
        employee.listAllAccounts();

        // Capture the output
        String output = outputStream.toString();
        String[] lines = output.split("\n");

        // Assert the expected output
        assertEquals("Enter username:", lines[0]);
        assertEquals("Invalid username", lines[1]);
    }
}