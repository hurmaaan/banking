package bankingTest;

import banking.LoanPending;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LoanPendingTest {

    @Test
    public void testSingletonInstance() {
        // Obtain two instances of LoanPending
        LoanPending instance1 = LoanPending.getInstance();
        LoanPending instance2 = LoanPending.getInstance();

        // Verify that both instances are the same (singleton behavior)
        assertNotNull(instance1);
        assertNotNull(instance2);
        assertSame(instance1, instance2, "LoanPending should be a singleton");
    }

    @Test
    public void testCanRepay() {
        // Call the canRepay() method on the singleton instance
        LoanPending instance = LoanPending.getInstance();
        boolean result = instance.canRepay();

        // Verify that canRepay() returns false
        assertFalse(result, "canRepay() should return false for LoanPending");
    }
}