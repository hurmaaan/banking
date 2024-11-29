package bankingTest;

import banking.LoanPaid;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class LoanPaidTest {

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
        // Obtain two instances of LoanPaid
        LoanPaid instance1 = LoanPaid.getInstance();
        LoanPaid instance2 = LoanPaid.getInstance();

        // Verify that both instances are the same (singleton behavior)
        assertNotNull(instance1);
        assertNotNull(instance2);
        assertSame(instance1, instance2, "LoanPaid should be a singleton");
    }

    @Test
    public void testCanRepay() {
        // Call the canRepay() method on the singleton instance
        LoanPaid instance = LoanPaid.getInstance();
        boolean result = instance.canRepay();

        // Verify that canRepay() returns false
        assertFalse(result, "canRepay() should return false for LoanPaid");

        // Verify that the appropriate message is printed
        assertTrue(outputStream.toString().contains("Loan alreay repaid!"));
    }

    @BeforeEach
    public void tearDown() {
        // Restore the original System.out
        System.setOut(originalOut);
    }
}