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
import banking.Checking;
import banking.LoanApplication;
import banking.Loans;
import banking.RepayLoan;
import banking.Savings;
import banking.User;
import banking.UserDatabase;

class LoansTest {

	private static Loans loans;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		Loans.reset();
		Bank.reset();
		LoanApplication.reset();
		UserDatabase.reset();
		Account.reset();
		loans = Loans.getInstance();
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
	void testSingleTonInstance() {
		Loans anotherInstance = Loans.getInstance();
		assertSame(loans, anotherInstance);
	}

	@Test

	void testCreateLoanApplication() {
		User u = UserDatabase.getInstance().findUser("customer");

		Account a = new Account(200, Savings.getInstance(), u);
		Account b = new Account(300, Checking.getInstance(), u);
		// other paths already tested//integration testing
		assertDoesNotThrow(() -> loans.createLoanApplication(a, 12, 500));

		// test for when an account already has a pending loan
		assertDoesNotThrow(() -> loans.createLoanApplication(a, 12, 30));
		// adding one more loan
		assertDoesNotThrow(() -> loans.createLoanApplication(b, 12, 600));

	}

	@Test
	void testListPendingLoans() {

		assertTrue(loans.approveLoan("3"));
		loans.listPendingLoans();
		String[] outputLines = getOutput().split("\n");
		assertEquals("--- Pending Loan Applications ---", outputLines[0].trim());
		assertEquals("Loan ID: 2, Account ID: 109, Amount: 500.0",
				outputLines[1].trim());
		assertTrue(loans.rejectLoan("2"));

	}

	@Test
	void testApproveLoan() {
		// 3 has already been approved so this should return false
		assertFalse(loans.approveLoan("3"));

	}

	@Test
	void testRejectLoan() {
		assertFalse(loans.rejectLoan("3"));
	}

	@Test
	void testRepayLoan_1() {
		RepayLoan r = new RepayLoan(30, "x");
		loans.repayLoan(r, "x", "y", 0);
		assertEquals("Either loan Id is invalid or loan has not been approved yet.", getOutput().trim());
	}

	@Test
	void testRepayLoan_2() throws Exception{
		Loans.reset();
//		LoanApplication.reset();
		User u = UserDatabase.getInstance().findUser("emp");
		Account a = new Account(50, Checking.getInstance(), u);
		Bank.getInstance().addAccount(a);
		loans.createLoanApplication(a, 12, 30);
		
		String[] lines = getOutput().split("\n");
		setOutput();
		
		String loanId = Character.toString(lines[2].trim().charAt(lines[2].trim().length()-1));
		assertTrue(loans.approveLoan(loanId));
		
		
		loans.createLoanApplication(a, 12, 30);
		lines = getOutput().split("\n");
		String loanId2 = Character.toString(lines[2].trim().charAt(lines[2].trim().length()-1));
		assertTrue(loans.approveLoan(loanId2));
		setOutput();
		RepayLoan r = new RepayLoan(30, loanId2);
		loans.repayLoan(r, loanId2, "emp", 30);
		
		assertEquals("Loan Fully Repaid!", getOutput().split("\n")[1].trim());
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
