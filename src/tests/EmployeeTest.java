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
import banking.Employee;
import banking.InputHandler;
import banking.Manager;
import banking.RecordedCommand;
import banking.UserDatabase;

class EmployeeTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {

		RecordedCommand.reset();
		Bank.reset();
		Account.reset();

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
	void testLogOut() {

		String input = "9\n";
		InputHandler.getInstance().setScanner(setInput(input));
		new Employee().displayMenu();

		String[] lines = getOutput().split("\n");
		assertEquals("Choose an option: Logging out...", lines[11].trim());
		InputHandler.getInstance().close();
	}

	@Test
	void testUndoEmpty() {
		// other possibilities have been tested in the CmdTest classes
		String input = "7 " + "\n9\n";
		InputHandler.getInstance()
				.setScanner(setInput(input));
		Employee employee = new Employee();
		employee.displayMenu();
		String[] lines = getOutput().split("\n");
		assertEquals("Choose an option: Nothing to undo.", lines[11].trim());
	}

	@Test
	void testRedoEmpty() {
		// other possibilities have been tested in the CmdTest classes
		String input = "8 " + "\n9\n";
		InputHandler.getInstance()
				.setScanner(setInput(input));
		Employee employee = new Employee();
		employee.displayMenu();
		String[] lines = getOutput().split("\n");
		assertEquals("Choose an option: Nothing to redo.", lines[11].trim());
	}

	@Test
	void testInvalid() {
		String input = "13 " + "\n9\n";
		InputHandler.getInstance()
				.setScanner(setInput(input));
		Employee employee = new Employee();
		employee.displayMenu();
		String[] lines = getOutput().split("\n");
		assertEquals("Choose an option: Invalid choice, please try again.", lines[11].trim());

	}

	@Test
	void testOpenAccount_1() {
		String input = "1 " + "\nee" + "\n20" + "\nSavings" + "\n9\n";
		InputHandler.getInstance()
				.setScanner(setInput(input));
		Employee employee = new Employee();
		employee.displayMenu();
		String[] lines = getOutput().split("\n");
		assertEquals(
				"Choose an option: Enter user ID for the account: Enter initial deposit amount: Enter account type (Savings/Checking): User does not exist!",
				lines[11].trim());

	}

	@Test
	void testOpenAccount_2() {
		String input = "1 " + "\ncustomer" + "\n-20" + "\nSavings" + "\n9\n";
		InputHandler.getInstance()
				.setScanner(setInput(input));
		Employee employee = new Employee();
		employee.displayMenu();
		String[] lines = getOutput().split("\n");
		assertEquals(
				"Choose an option: Enter user ID for the account: Enter initial deposit amount: Enter account type (Savings/Checking): Invalid amount entered.",
				lines[11].trim());
		assertEquals("Aborting Transaction...", lines[12].trim());

	}

	@Test
	void testOpenAccount_3() {
		String input = "1 " + "\ncustomer" + "\ns4" + "\nSavings" + "\n9\n";
		InputHandler.getInstance()
				.setScanner(setInput(input));
		Employee employee = new Employee();
		employee.displayMenu();

		String[] lines = getOutput().split("\n");
		assertEquals(
				"Choose an option: Enter user ID for the account: Enter initial deposit amount: Enter account type (Savings/Checking): Invalid amount entered.",
				lines[11].trim());

	}

	@Test
	void testOpenAccount_4() {
		String input = "1 " + "\ncustomer" + "\n20" + "\nSavings" + "\n9\n";
		InputHandler.getInstance()
				.setScanner(setInput(input));
		assertFalse(Bank.getInstance().hasAccount(UserDatabase.getInstance().findUser("customer")));

		Employee employee = new Employee();
		employee.displayMenu();
		assertTrue(Bank.getInstance().hasAccount(UserDatabase.getInstance().findUser("customer")));

		String[] lines = getOutput().split("\n");
		assertEquals(
				"Choose an option: Enter user ID for the account: Enter initial deposit amount: Enter account type (Savings/Checking): Account Opened Successfully!",
				lines[11].trim());

	}

	@Test
	void testToString() {
		assertEquals("Employee", new Employee().toString());
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
