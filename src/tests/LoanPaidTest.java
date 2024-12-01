package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import banking.LoanPaid;

class LoanPaidTest {

	private LoanPaid loanPaid;

	@BeforeEach
	void setUp() throws Exception {
		loanPaid = LoanPaid.getInstance();
		setOutput();
	}

	@Test
	void testSingletonInstance() {

		LoanPaid anotherInstance = LoanPaid.getInstance();
		assertSame(loanPaid, anotherInstance);
	}

	@Test
	void testCanRepay() {
		String output = getOutput();
		assertEquals("Loan alreay repaid!", output.trim());
		assertFalse(loanPaid.canRepay());
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
