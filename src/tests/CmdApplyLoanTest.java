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
import banking.Bank;
import banking.CmdApplyLoan;
import banking.LoanApplication;
import banking.Loans;
import banking.Savings;
import banking.User;
import banking.UserDatabase;

class CmdApplyLoanTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {

		Account.reset();
		Bank.reset();
		Loans.reset();
		LoanApplication.reset();
		setOutput();

	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testInvalid() {
		new CmdApplyLoan().execute(new String[] { "12", "200", "12", "customer" });
		assertEquals("Invalid account id", getOutput().trim());
	}

	@Test
	void testValid() {
		User u = UserDatabase.getInstance().findUser("customer");

		Account a = new Account(200, Savings.getInstance(), u);
		Bank.getInstance().addAccount(a);
		new CmdApplyLoan().execute(new String[] { "109", "200", "12", "customer" });
		assertEquals("Applied Successfuly for a loan.", getOutput().split("\n")[0].trim());

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
