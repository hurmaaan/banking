package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import banking.Account;
import banking.Bank;
import banking.Client;
import banking.Employee;
import banking.InputHandler;
import banking.LoanApplication;
import banking.Loans;
import banking.RecordedCommand;

class ClientTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		setOutput();
		Bank.reset();
		Account.reset();
		Loans.reset();
		LoanApplication.reset();

	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testInvalid() {
		String input = "13 " + "\n8\n";
		InputHandler.getInstance()
				.setScanner(setInput(input));

		new Client("test").displayMenu();
		String[] lines = getOutput().split("\n");
		assertEquals("Choose an option: Invalid choice, please try again.", lines[10].trim());
	}

	@Test
	void testLogOut() {
		String input = "8\n";
		InputHandler.getInstance()
				.setScanner(setInput(input));
		new Client("test").displayMenu();
		String[] lines = getOutput().split("\n");
		assertEquals("Choose an option: Logging out...", lines[10].trim());
	}

	@Test
	void testViewAccountDetails() {
		String input = "1 " + "\n8\n";
		InputHandler.getInstance()
				.setScanner(setInput(input));

		assertDoesNotThrow(() -> new Client("customer").displayMenu());
	}

	@Test
	void testApplyForLoan_1() {
		String input = "5 " + "\n102" + "\n3000" + "\nj" + "\n8\n";
		InputHandler.getInstance()
				.setScanner(setInput(input));
		new Client("customer").displayMenu();

		String[] lines = getOutput().split("\n");
		assertEquals(
				"Choose an option: Enter your account ID: Enter loan amount: Enter loan term (in months): Invalid amount entered.",
				lines[10].trim());

	}

	@Test
	void testApplyForLoan_2() {
		String input = "5 " + "\n102" + "\n3000" + "\n0" + "\n8\n";
		InputHandler.getInstance()
				.setScanner(setInput(input));
		new Client("customer").displayMenu();

		String[] lines = getOutput().split("\n");
		assertEquals(
				"Choose an option: Enter your account ID: Enter loan amount: Enter loan term (in months): Invalid value(s) entered.",
				lines[10].trim());

		assertEquals(
				"Aborting Transaction...",
				lines[11].trim());
	}

	@Test
	void testApplyForLoan_3() {
		String input = "5 " + "\n102" + "\n-3000" + "\n30" + "\n8\n";
		InputHandler.getInstance()
				.setScanner(setInput(input));
		new Client("customer").displayMenu();

		String[] lines = getOutput().split("\n");
		assertEquals(
				"Choose an option: Enter your account ID: Enter loan amount: Enter loan term (in months): Invalid value(s) entered.",
				lines[10].trim());

		assertEquals(
				"Aborting Transaction...",
				lines[11].trim());
	}

	@Test
	void testApplyForLoan_4() {
		String input = "5 " + "\n102" + "\n3000" + "\n30" + "\n8\n";
		InputHandler.getInstance()
				.setScanner(setInput(input));
		assertDoesNotThrow(() -> new Client("customer").displayMenu());

	}

	@Test
	void testDeposit() {

		String input = "2 " + "\n102" + "\n-3000" + "\n8\n";
		InputHandler.getInstance()
				.setScanner(setInput(input));
		assertDoesNotThrow(() -> new Client("customer").displayMenu());
		String[] lines = getOutput().split("\n");
		assertEquals("Choose an option: Enter your account number: Enter amount to depositInvalid amount entered.",
				lines[10].trim());
		assertEquals("Aborting Transaction...", lines[11].trim());

	}

	@Test
	void testDeposit_2() {

		String input = "2 " + "\n102" + "\nhg" + "\n8\n";
		InputHandler.getInstance()
				.setScanner(setInput(input));
		assertDoesNotThrow(() -> new Client("customer").displayMenu());
		String[] lines = getOutput().split("\n");
		assertEquals(
				"Choose an option: Enter your account number: Enter amount to depositInvalid amount entered.",
				lines[10].trim());

	}

	@Test
	void testDeposit_3() {

		String input = "2 " + "\n102" + "\n200" + "\n8\n";
		InputHandler.getInstance()
				.setScanner(setInput(input));
		assertDoesNotThrow(() -> new Client("customer").displayMenu());
		String[] lines = getOutput().split("\n");

		assertEquals(
				"Choose an option: Enter your account number: Enter amount to depositInvalid account id",
				lines[10].trim());

	}

	@Test
	void testWithdrawal() {

		String input = "3 " + "\n102" + "\n-3000" + "\n8\n";
		InputHandler.getInstance()
				.setScanner(setInput(input));
		assertDoesNotThrow(() -> new Client("customer").displayMenu());
		String[] lines = getOutput().split("\n");
		assertEquals("Choose an option: Enter your account number: Enter amount to withdrawInvalid amount entered.",
				lines[10].trim());
		assertEquals("Aborting Transaction...", lines[11].trim());

	}

	@Test
	void testWithdrawal_2() {

		String input = "3 " + "\n102" + "\nhg" + "\n8\n";
		InputHandler.getInstance()
				.setScanner(setInput(input));
		assertDoesNotThrow(() -> new Client("customer").displayMenu());
		String[] lines = getOutput().split("\n");
		assertEquals(
				"Choose an option: Enter your account number: Enter amount to withdrawInvalid amount entered.",
				lines[10].trim());

	}

	@Test
	void testWithdrawal_3() {

		String input = "3 " + "\n102" + "\n200" + "\n8\n";
		InputHandler.getInstance()
				.setScanner(setInput(input));
		assertDoesNotThrow(() -> new Client("customer").displayMenu());
		String[] lines = getOutput().split("\n");

		assertEquals(
				"Choose an option: Enter your account number: Enter amount to withdrawInvalid account id",
				lines[10].trim());

	}

	@Test
	void testTransfer() {

		String input = "4 " + "\n102" + "\n102" + "\n8\n";
		InputHandler.getInstance()
				.setScanner(setInput(input));
		assertDoesNotThrow(() -> new Client("customer").displayMenu());
		String[] lines = getOutput().split("\n");
		assertEquals(
				"Choose an option: Enter your account number: Enter other account number: Cannot transfer to the same account!",
				lines[10].trim());

	}

	@Test
	void testTransfer_2() {

		String input = "4 " + "\n102" + "\n103" + "\n-20" + "\n8\n";
		InputHandler.getInstance()
				.setScanner(setInput(input));
		assertDoesNotThrow(() -> new Client("customer").displayMenu());
		String[] lines = getOutput().split("\n");
		assertEquals(
				"Choose an option: Enter your account number: Enter other account number: Enter amount to transfer:Invalid amount entered.",
				lines[10].trim());
		assertEquals("Aborting Transaction...", lines[11].trim());

	}

	@Test
	void testTransfer_3() {

		String input = "4 " + "\n102" + "\n103" + "\nf" + "\n8\n";
		InputHandler.getInstance()
				.setScanner(setInput(input));
		assertDoesNotThrow(() -> new Client("customer").displayMenu());
		String[] lines = getOutput().split("\n");

		assertEquals(
				"Choose an option: Enter your account number: Enter other account number: Enter amount to transfer:Invalid amount entered.",
				lines[10].trim());

	}

	@Test
	void testTransfer_4() {

		String input = "4 " + "\n102" + "\n103" + "\n100" + "\n8\n";
		InputHandler.getInstance()
				.setScanner(setInput(input));
		assertDoesNotThrow(() -> new Client("customer").displayMenu());
		String[] lines = getOutput().split("\n");

		assertEquals(
				"Choose an option: Enter your account number: Enter other account number: Enter amount to transfer:Invalid Sender Account ID",
				lines[10].trim());

	}

	@Test
	void testListTransactions() {
		String input = "7 " + "\n102" + "\n8\n";
		InputHandler.getInstance()
				.setScanner(setInput(input));
		assertDoesNotThrow(() -> new Client("customer").displayMenu());
		String[] lines = getOutput().split("\n");
		assertEquals(
				"Choose an option: Enter your accountIdInvalid account Id",
				lines[10].trim());
	}

	@Test
	void testRepayLoan() {

		String input = "6 " + "\n102" + "\n-100" + "\n8\n";
		InputHandler.getInstance()
				.setScanner(setInput(input));
		assertDoesNotThrow(() -> new Client("customer").displayMenu());
		String[] lines = getOutput().split("\n");
		assertEquals(
				"Choose an option: Enter Loan Id: Enter Amount to Repay: Invalid amount entered.",
				lines[10].trim());

	}

	@Test
	void testRepayLoan_1() {

		String input = "6 " + "\n102" + "\nj" + "\n8\n";
		InputHandler.getInstance()
				.setScanner(setInput(input));
		assertDoesNotThrow(() -> new Client("customer").displayMenu());
		String[] lines = getOutput().split("\n");
		assertEquals(
				"Choose an option: Enter Loan Id: Enter Amount to Repay: Invalid amount entered.",
				lines[10].trim());

	}

	@Test
	void testRepayLoan_2() {

		String input = "6 " + "\n102" + "\n200" + "\n8\n";
		InputHandler.getInstance()
				.setScanner(setInput(input));
		assertDoesNotThrow(() -> new Client("customer").displayMenu());
		String[] lines = getOutput().split("\n");

		assertEquals(
				"Choose an option: Enter Loan Id: Enter Amount to Repay: Either loan Id is invalid or loan has not been approved yet.",
				lines[10].trim());

	}

	@Test
	void testToString() {
		assertEquals("Client", new Client("user").toString());
	}

	static InputStream setInput(String input) {
		byte[] bytes = input.replace("\n", System.lineSeparator()).getBytes();
		return new ByteArrayInputStream(bytes);
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
