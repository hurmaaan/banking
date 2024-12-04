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
import banking.Checking;
import banking.Employee;
import banking.InputHandler;
import banking.Manager;
import banking.RecordedCommand;
import banking.User;
import banking.UserDatabase;

class EmployeeTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {

	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		setOutput();

		RecordedCommand.reset();
		Bank.reset();
		Account.reset();
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testLogOut() {

		String input = "9\n";
		InputHandler.getInstance().setScanner(setInput(input));
		new Employee("test").displayMenu();

		String[] lines = getOutput().split("\n");
		assertEquals("Choose an option: Logging out...", lines[12].trim());
		InputHandler.getInstance().close();
	}

	@Test
	void testUndoEmpty() {
		// other possibilities have been tested in the CmdTest classes
		String input = "7 " + "\n9\n";
		InputHandler.getInstance()
				.setScanner(setInput(input));
		Employee employee = new Employee("test");
		employee.displayMenu();
		String[] lines = getOutput().split("\n");
		assertEquals("Choose an option: Nothing to undo.", lines[12].trim());
	}

	@Test
	void testRedoEmpty() {
		// other possibilities have been tested in the CmdTest classes
		String input = "8 " + "\n9\n";
		InputHandler.getInstance()
				.setScanner(setInput(input));
		Employee employee = new Employee("test");
		employee.displayMenu();
		String[] lines = getOutput().split("\n");
		assertEquals("Choose an option: Nothing to redo.", lines[12].trim());
	}

	@Test
	void testInvalid() {
		String input = "13 " + "\n9\n";
		InputHandler.getInstance()
				.setScanner(setInput(input));
		Employee employee = new Employee("test");
		employee.displayMenu();
		String[] lines = getOutput().split("\n");
		assertEquals("Choose an option: Invalid choice, please try again.", lines[12].trim());

	}

	@Test
	void testOpenAccount_1() {
		String input = "1 " + "\nee" + "\n20" + "\nSavings" + "\n9\n";
		InputHandler.getInstance()
				.setScanner(setInput(input));
		Employee employee = new Employee("test");
		employee.displayMenu();
		String[] lines = getOutput().split("\n");
		assertEquals(
				"Choose an option: Enter user ID for the account: Enter initial deposit amount: Enter account type (Savings/Checking): User does not exist!",
				lines[12].trim());

	}

	@Test
	void testOpenAccount_2() {
		String input = "1 " + "\ncustomer" + "\n-20" + "\nSavings" + "\n9\n";
		InputHandler.getInstance()
				.setScanner(setInput(input));
		Employee employee = new Employee("test");
		employee.displayMenu();
		String[] lines = getOutput().split("\n");
		assertEquals(
				"Choose an option: Enter user ID for the account: Enter initial deposit amount: Enter account type (Savings/Checking): Invalid amount entered.",
				lines[12].trim());
		assertEquals("Aborting Transaction...", lines[13].trim());

	}

	@Test
	void testOpenAccount_3() {
		String input = "1 " + "\ncustomer" + "\ns4" + "\nSavings" + "\n9\n";
		InputHandler.getInstance()
				.setScanner(setInput(input));
		Employee employee = new Employee("test");
		employee.displayMenu();

		String[] lines = getOutput().split("\n");
		assertEquals(
				"Choose an option: Enter user ID for the account: Enter initial deposit amount: Enter account type (Savings/Checking): Invalid amount entered.",
				lines[12].trim());

	}

	@Test
	void testOpenAccount_4() {
		String input = "1 " + "\ncustomer" + "\n20" + "\nSavings" + "\n9\n";
		InputHandler.getInstance()
				.setScanner(setInput(input));
		assertFalse(Bank.getInstance().hasAccount(UserDatabase.getInstance().findUser("customer")));

		Employee employee = new Employee("test");
		employee.displayMenu();
		assertTrue(Bank.getInstance().hasAccount(UserDatabase.getInstance().findUser("customer")));

		String[] lines = getOutput().split("\n");
		assertEquals(
				"Choose an option: Enter user ID for the account: Enter initial deposit amount: Enter account type (Savings/Checking): Account Opened Successfully!",
				lines[12].trim());

	}

	@Test

	void testCloseAccount() {
		String input = "2 " + "\n109" + "\n9\n";
		InputHandler.getInstance()
				.setScanner(setInput(input));

		User u = UserDatabase.getInstance().findUser("customer");
		Account a = new Account(0, Checking.getInstance(), u);

		Bank.getInstance().addAccount(a);
		assertTrue(Bank.getInstance().hasAccount(u));
		Employee employee = new Employee("test");
		employee.displayMenu();
		assertFalse(Bank.getInstance().hasAccount(u));

		String[] lines = getOutput().split("\n");
		// integration testing, all other branches have already been tested
		assertEquals(
				"Choose an option: Enter Account Id to close:Account Closed Successfully!",
				lines[12].trim());
	}

	@Test
	void testRegisterClient() {
		String input = "6 " + "\ncustomer" + "\n9\n";
		InputHandler.getInstance()
				.setScanner(setInput(input));

		Employee employee = new Employee("test");
		employee.displayMenu();

		String[] lines = getOutput().split("\n");

		assertEquals("Choose an option: Enter new username:Username already exists!", lines[12].trim());
	}

	@Test
	void testRegisterClient_2() {
		String input = "6 " + "\ncustomer2" + "\nabc" + "\nbca" + "\nabc" + "\nabc" + "\n9\n";
		InputHandler.getInstance()
				.setScanner(setInput(input));
		assertNull(UserDatabase.getInstance().findUser("customer2"));

		Employee employee = new Employee("test");
		employee.displayMenu();
		assertNotNull(UserDatabase.getInstance().findUser("customer2"));

		String[] lines = getOutput().split("\n");
		assertEquals(
				"Choose an option: Enter new username:Enter PasswordRe-enter PasswordPasswords do not match. Try again.",
				lines[12].trim());
		assertEquals("Enter PasswordRe-enter PasswordUser added successfully!", lines[13].trim());
	}

	@Test
	void testListAccounts_1() {
		String input = "5 " + "\ncustomer3" + "\n9\n";
		InputHandler.getInstance()
				.setScanner(setInput(input));

		Employee employee = new Employee("test");
		employee.displayMenu();
		String[] lines = getOutput().split("\n");

		assertEquals("Choose an option: Enter username:Invalid username", lines[12].trim());

	}

	@Test
	void testListAccounts_2() {
		String input = "5 " + "\ncustomer" + "\n9\n";
		InputHandler.getInstance()
				.setScanner(setInput(input));

		Employee employee = new Employee("test");
		employee.displayMenu();
		String[] lines = getOutput().split("\n");

		assertEquals("Choose an option: Enter username:Accounts belonging to customer", lines[12].trim());

	}

	@Test

	void testListPendingLoans() {
		String input = "3 " + "\n9\n";
		InputHandler.getInstance()
				.setScanner(setInput(input));
		Employee employee = new Employee("test");
		// integration testing
		assertDoesNotThrow(() -> employee.displayMenu());

	}

	@Test
	void testProcessInvalid() {
		String input = "4 " + "\n2" + "\n3" + "\n9\n";
		InputHandler.getInstance()
				.setScanner(setInput(input));
		Employee employee = new Employee("test");
		employee.displayMenu();
		String[] lines = getOutput().split("\n");
		assertEquals("Choose an option: WARNING!!! This action cannot be undone!", lines[12].trim());
		assertEquals("Enter loan application ID: 1. Approve Loan", lines[13].trim());
		assertEquals("2. Reject Loan", lines[14].trim());
		assertEquals("Choose an option: Invalid option.", lines[15].trim());

	}

	@Test
	void testProcessApprove() {
		String input = "4 " + "\n2" + "\n1" + "\n9\n";
		InputHandler.getInstance()
				.setScanner(setInput(input));
		Employee employee = new Employee("test");
		assertDoesNotThrow(() -> employee.displayMenu());
		String[] lines = getOutput().split("\n");
		assertEquals("Choose an option: WARNING!!! This action cannot be undone!", lines[12].trim());
		assertEquals("Enter loan application ID: 1. Approve Loan", lines[13].trim());
		assertEquals("2. Reject Loan", lines[14].trim());

	}

	@Test
	void testProcessReject() {
		String input = "4 " + "\n2" + "\n2" + "\n9\n";
		InputHandler.getInstance()
				.setScanner(setInput(input));
		Employee employee = new Employee("test");
		assertDoesNotThrow(() -> employee.displayMenu());
		String[] lines = getOutput().split("\n");
		assertEquals("Choose an option: WARNING!!! This action cannot be undone!", lines[12].trim());
		assertEquals("Enter loan application ID: 1. Approve Loan", lines[13].trim());
		assertEquals("2. Reject Loan", lines[14].trim());

	}

	@Test
	void testClientMenu() {
		String input = "10 " + "\n8\n";
		InputHandler.getInstance()
				.setScanner(setInput(input));
		Employee employee = new Employee("test");
		assertDoesNotThrow(() -> employee.displayMenu());
		String[] lines = getOutput().split("\n");
		assertEquals("--- Client Menu ---", lines[13].trim());
	}

	@Test
	void testToString() {
		assertEquals("Employee", new Employee("test").toString());
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
