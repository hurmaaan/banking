package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import banking.LoanPending;

class LoanPendingTest {

    private LoanPending loanPending;

    @BeforeEach
    void setUp() {
        loanPending = LoanPending.getInstance();
    }

    @Test
    void testSingletonInstance() {
        LoanPending anotherInstance = LoanPending.getInstance();
        assertSame(loanPending, anotherInstance);
    }

    @Test
    void testCanRepay() {
        assertFalse(loanPending.canRepay());
    }
}
