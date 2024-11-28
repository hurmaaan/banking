package bankingTest;

import org.junit.jupiter.api.Test;
import banking.*;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class CheckingTest {
    @Test
    void testSetInterestRate() throws NoSuchFieldException, IllegalAccessException {
        Checking checking = Checking.getInstance();
        double expectedRate = 3.5;

        checking.setInteresetRate(expectedRate);

        // Use reflection to access the private field
        Field interestRateField = Checking.class.getDeclaredField("interestRate");
        interestRateField.setAccessible(true);
        double actualRate = (double) interestRateField.get(checking);

        // Assert the interest rate is set correctly
        assertEquals(expectedRate, actualRate);
    }
    
    @Test
    void testToString() {
        Checking checking = Checking.getInstance();
        assertEquals("Checking", checking.toString());
    }
}
