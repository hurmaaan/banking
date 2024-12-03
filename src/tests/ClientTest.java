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
