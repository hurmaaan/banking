package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import banking.LoanRejected;

class LoanRejectedTest {

	private LoanRejected loanRejected;

	@BeforeEach
	void setUp() throws Exception {
		loanRejected = LoanRejected.getInstance();
		setOutput();
	}

	@Test
	void testSingletonInstance() {

		LoanRejected anotherInstance = LoanRejected.getInstance();
		assertSame(loanRejected, anotherInstance);
	}

	@Test
	void testCanRepay() {

		assertFalse(loanRejected.canRepay());
		String output = getOutput();
		assertEquals("Loan Rejected. Cannot Proceed.", output.trim());

	}

	PrintStream oldPrintStream;
	ByteArrayOutputStream bos;

	private void setOutput() throws Exception {
		oldPrintStream = System.out;
		bos = new ByteArrayOutputStream();
		System.setOut(new PrintStream(bos));
	}

	private String getOutput() { // throws Exception
		System.setOut(oldPrintStream);
		return bos.toString();

	}
}
