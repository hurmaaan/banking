package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import banking.Account;
import banking.CmdRejectLoan;
import banking.LoanApplication;
import banking.Loans;
import banking.Savings;
import banking.User;
import banking.UserDatabase;

class CmdRejectLoanTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		setOutput();
		Loans.reset();
		LoanApplication.reset();
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testInvalid() {
		new CmdRejectLoan().execute(new String[] { "1" });
		assertEquals("Invalid Loan Id", getOutput().trim());
	}

	@Test
	void testValid() {
		User u = UserDatabase.getInstance().findUser("customer");

		Account a = new Account(200, Savings.getInstance(), u);
		// other paths already tested//integration testing
		assertDoesNotThrow(() -> Loans.getInstance().createLoanApplication(a, 12, 500));
		new CmdRejectLoan().execute(new String[] { "2" });
		assertEquals("Loan Rejected Successfully", getOutput().split("\n")[3].trim());

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
