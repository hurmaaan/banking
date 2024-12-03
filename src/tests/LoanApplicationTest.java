package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import banking.Account;
import banking.LoanApplication;
import banking.Loans;
import banking.Savings;
import banking.User;
import banking.UserDatabase;

class LoanApplicationTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {

	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		setOutput();
		LoanApplication.reset();
		Loans.reset();
		Account.reset();
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testRejectApproveLoan() {
		LoanApplication loan = new LoanApplication(null, 23);
		assertDoesNotThrow(() -> loan.rejectLoan());
		assertDoesNotThrow(() -> loan.approveLoan());
	}

	@Test
	void testCreateLoanApplication_1() {
		User u = UserDatabase.getInstance().findUser("customer");

		Account a = new Account(200, Savings.getInstance(), u);

		LoanApplication loan = new LoanApplication(a, 23);
		ArrayList<LoanApplication> loans = new ArrayList<>();
		loans.add(loan);
		LoanApplication.createLoanApplication(loans, a, 12, 5000);
		assertEquals("This account already has a pending loan. Cannot Apply For a new Loan.", getOutput().trim());
	}

	@Test
	void testCreateLoanApplication_2() {
		User u = UserDatabase.getInstance().findUser("customer");

		Account a = new Account(200, Savings.getInstance(), u);
		Account acc2 = new Account(200, Savings.getInstance(), u);

		LoanApplication loan = new LoanApplication(acc2, 12);

		ArrayList<LoanApplication> loans = new ArrayList<>();
		loans.add(loan);
		LoanApplication.createLoanApplication(loans, a, 12, 5000);
		double monthlyPayment = a.calculateMonthlyPayment(12, 5000);// already tested

		String[] outputLines = getOutput().split("\n");

		assertEquals("Applied Successfuly for a loan.", outputLines[0].trim());
		assertEquals("Monthly Payment: " + monthlyPayment, outputLines[1].trim());
		assertEquals("Loan ID: 3", outputLines[2].trim());

	}

	@Test

	void testListPendingLoans() {
		User u = UserDatabase.getInstance().findUser("customer");

		Account a = new Account(200, Savings.getInstance(), u);

		LoanApplication loan = new LoanApplication(a, 23);
		ArrayList<LoanApplication> loans = new ArrayList<>();
		loans.add(loan);

		LoanApplication.listPendingLoans(loans);
		String[] outputLines = getOutput().split("\n");
		assertEquals("--- Pending Loan Applications ---", outputLines[0].trim());
		assertEquals("Loan ID: 2, Account ID: 109, Amount: 23.0", outputLines[1].trim());

	}

	@Test
	void testUniqueLoanID() {
		LoanApplication loan = new LoanApplication(null, 23);
		LoanApplication loan2 = new LoanApplication(null, 23);

		assertEquals("2", loan.toString());
		assertEquals("3", loan2.toString());
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
