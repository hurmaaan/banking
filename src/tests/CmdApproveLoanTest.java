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
import banking.Checking;
import banking.CmdApproveLoan;
import banking.LoanApplication;
import banking.Loans;
import banking.Savings;
import banking.User;
import banking.UserDatabase;

class CmdApproveLoanTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		setOutput();
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testInvalidLoan() {
		Loans.reset();
		new CmdApproveLoan().execute(new String[] { "3" });
		assertEquals("Invalid Loan Id", getOutput().trim());

	}

	@Test
	void testValidLoan() {
		Loans.reset();
		LoanApplication.reset();
		User u = UserDatabase.getInstance().findUser("customer");

		Account a = new Account(200, Savings.getInstance(), u);
		// other paths already tested//integration testing
		assertDoesNotThrow(() -> Loans.getInstance().createLoanApplication(a, 12, 500));
		new CmdApproveLoan().execute(new String[] { "2" });
		assertEquals("Loan Approved Successfully", getOutput().split("\n")[3].trim());

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
