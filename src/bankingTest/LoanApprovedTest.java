package bankingTest;

import banking.LoanApproved;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LoanApprovedTest {

    @Test
    public void testSingletonInstance() {
        // Obtain two instances of LoanApproved
        LoanApproved instance1 = LoanApproved.getInstance();
        LoanApproved instance2 = LoanApproved.getInstance();

        // Verify that both instances are the same (singleton behavior)
        assertNotNull(instance1);
        assertNotNull(instance2);
        assertSame(instance1, instance2, "LoanApproved should be a singleton");
    }

    @Test
    public void testCanRepay() {
        // Verify that canRepay() returns true for LoanApproved
        LoanApproved instance = LoanApproved.getInstance();
        assertTrue(instance.canRepay(), "canRepay() should return true for LoanApproved");
    }
}