package bankingTest;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import banking.InputHandler;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

public class InputHandlerTest {

    private InputHandler inputHandler;

    @BeforeEach
    public void setUp() {
        // Reset the singleton before each test
        InputHandler.resetInstance();

        // Initialize the InputHandler instance
        inputHandler = InputHandler.getInstance();
    }

    @AfterEach
    public void tearDown() {
        // Reset the singleton after each test (to ensure clean state)
        InputHandler.resetInstance();
    }

    @Test
    public void testGetNextLine() {
        // Simulate a line of user input
        String simulatedInput = "Hello, World!\n";
        inputHandler.setScanner(new Scanner(new ByteArrayInputStream(simulatedInput.getBytes())));

        // Verify the `getNextLine` method
        String result = inputHandler.getNextLine();
        assertEquals("Hello, World!", result);
    }

    @Test
    public void testGetNextInt() {
        // Simulate an integer input
        String simulatedInput = "42\n";
        inputHandler.setScanner(new Scanner(new ByteArrayInputStream(simulatedInput.getBytes())));

        // Verify the `getNextInt` method
        int result = inputHandler.getNextInt();
        assertEquals(42, result);
    }

    @Test
    public void testGetNextDouble() {
        // Simulate a double input
        String simulatedInput = "3.14\n";
        inputHandler.setScanner(new Scanner(new ByteArrayInputStream(simulatedInput.getBytes())));

        // Verify the `getNextDouble` method
        double result = inputHandler.getNextDouble();
        assertEquals(3.14, result, 0.0001); // Allowing a small delta for floating-point comparison
    }

    @Test
    public void testClose() {
        // Create a mock scanner and inject it
        Scanner mockScanner = new Scanner(new ByteArrayInputStream("test".getBytes()));
        inputHandler.setScanner(mockScanner);

        // Close the scanner
        inputHandler.close();

        // Verify that the scanner is closed
        assertThrows(IllegalStateException.class, mockScanner::nextLine);
    }

    @Test
    public void testSingletonBehavior() {
        // Verify that the singleton instance is the same across calls
        InputHandler instance1 = InputHandler.getInstance();
        InputHandler instance2 = InputHandler.getInstance();

        assertSame(instance1, instance2);
    }

    @Test
    public void testResetInstance() {
        // Get the first instance
        InputHandler instance1 = InputHandler.getInstance();

        // Reset the singleton
        InputHandler.resetInstance();

        // Get a new instance
        InputHandler instance2 = InputHandler.getInstance();

        // Verify that the instances are not the same
        assertNotSame(instance1, instance2);
    }
}