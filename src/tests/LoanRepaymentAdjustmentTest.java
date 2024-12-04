package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import banking.LoanRepaymentAdjustment;
import banking.Transaction;

class LoanRepaymentAdjustmentTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void test() {
		Transaction t = new LoanRepaymentAdjustment(2.3);
		t.execute(new String[] {"3"});
		assertEquals("Loan repayment Adjustment: 2.3 from loanId: 3", t.toString());
	}

}
