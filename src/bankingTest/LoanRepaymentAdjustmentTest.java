package bankingTest;

import banking.LoanRepaymentAdjustment;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LoanRepaymentAdjustmentTest {

    @Test
    public void testConstructor() {
        // Create an instance of LoanRepaymentAdjustment with a specific amount
        LoanRepaymentAdjustment adjustment = new LoanRepaymentAdjustment(150.0);

        // Verify that the instance is created successfully
        assertNotNull(adjustment, "LoanRepaymentAdjustment instance should be created successfully");
    }

    @Test
    public void testExecuteDoesNothing() {
        // Create an instance of LoanRepaymentAdjustment
        LoanRepaymentAdjustment adjustment = new LoanRepaymentAdjustment(150.0);

        // Execute the method with any command parts (this should do nothing)
        String[] cmdParts = {"dummyCommand"};
        adjustment.execute(cmdParts);

        // Verify that execute() does nothing (no exceptions, no state changes)
        assertTrue(true, "The execute() method should do nothing and not throw exceptions");
    }

    @Test
    public void testToString() {
        // Create an instance of LoanRepaymentAdjustment
        LoanRepaymentAdjustment adjustment = new LoanRepaymentAdjustment(150.0);

        // Verify the overridden toString() method includes the correct details
        String expectedString = "Loan repayment Adjustment: 150.0";
        String actualString = adjustment.toString();

        // Assert that the actual string matches the expected format
        assertEquals(expectedString, actualString, "The toString() method should return the correct formatted string");
    }
}