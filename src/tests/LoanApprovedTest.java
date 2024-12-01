package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import banking.LoanApproved;

class LoanApprovedTest {
	private LoanApproved loanApproved;

	@BeforeEach
	void setUp() throws Exception {
		loanApproved = LoanApproved.getInstance();
	}

	@Test
	void testSingletonInstance() {

		LoanApproved anotherInstance = LoanApproved.getInstance();
		assertSame(loanApproved, anotherInstance);
	}

	@Test
	void testCanRepay() {
		assertTrue(loanApproved.canRepay());

	}

}
