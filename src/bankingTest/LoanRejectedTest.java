package bankingTest;

import banking.LoanRejected;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class LoanRejectedTest {

    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private PrintStream originalOut;

    @BeforeEach
    public void setUp() {
        // Redirect System.out to capture output
        originalOut = System.out;
        System.setOut(new PrintStream(outputStream));
    }

    @Test
    public void testSingletonInstance() {
        // Obtain two instances of LoanRejected
        LoanRejected instance1 = LoanRejected.getInstance();
        LoanRejected instance2 = LoanRejected.getInstance();

        // Verify that both instances are the same (singleton behavior)
        assertNotNull(instance1);
        assertNotNull(instance2);
        assertSame(instance1, instance2, "LoanRejected should be a singleton");
    }

    @Test
    public void testCanRepay() {
        // Call the canRepay() method on the singleton instance
        LoanRejected instance = LoanRejected.getInstance();
        boolean result = instance.canRepay();

        // Verify that canRepay() returns false
        assertFalse(result, "canRepay() should return false for LoanRejected");

        // Verify that the appropriate message is printed
        assertTrue(outputStream.toString().contains("Loan Rejected. Cannot Proceed."));
    }

    @BeforeEach
    public void tearDown() {
        // Restore the original System.out
        System.setOut(originalOut);
    }
}