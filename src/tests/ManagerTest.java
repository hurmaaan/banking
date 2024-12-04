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

import banking.Employee;
import banking.InputHandler;
import banking.Manager;
import banking.User;
import banking.UserDatabase;

class ManagerTest {
	// private static User user = new User("test3", "test123", new Manager());

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
	void testLogOut() {

		String input = "5\n";
		InputHandler.getInstance().setScanner(setInput(input));
		new Manager("test").displayMenu();

		String[] lines = getOutput().split("\n");
		assertEquals("Logging out...", lines[9].trim());
		InputHandler.getInstance().close();
	}

	@Test
	void testSetInterestRates() {
		String input = "1 " + "\n0.02" + "\n0.03" + "\n5\n";
		InputHandler.getInstance()
				.setScanner(setInput(input));

		Manager manager = new Manager("test");
		manager.displayMenu();

		String[] lines = getOutput().split("\n");

		assertEquals("Set Interest Rates for Account Types:", lines[9].trim());
		assertEquals(
				"Enter interest rate for Savings Account (e.g., 0.03 for 3%): Enter interest rate for Checking Account (e.g., 0.01 for 1%):"
						.trim(),
				lines[10].trim());

	}

	@Test
	void testUndoEmpty() {
		// undo for all the commands have already been tested in their respective
		// CmdTest files
		String input = "3 " + "\n5\n";
		InputHandler.getInstance()
				.setScanner(setInput(input));

		Manager manager = new Manager("test");
		manager.displayMenu();

		String[] lines = getOutput().split("\n");
		assertEquals("Choose an option: Nothing to undo.", lines[8].trim());
	}

	@Test
	void testRedoEmpty() {
		// redo for all the commands have already been tested in their respective
		// CmdTest files
		String input = "4 " + "\n5\n";
		InputHandler.getInstance()
				.setScanner(setInput(input));

		Manager manager = new Manager("test");
		manager.displayMenu();

		String[] lines = getOutput().split("\n");
		assertEquals("Choose an option: Nothing to redo.", lines[8].trim());
	}

	@Test
	void testManageEmployeesInvalid() {
		String input = "2 " + "\n3" + "\n5\n";
		InputHandler.getInstance()
				.setScanner(setInput(input));

		Manager manager = new Manager("test");
		manager.displayMenu();

		String[] lines = getOutput().split("\n");
		assertEquals("Choose an option: Employee Management:", lines[8].trim());
		assertEquals("1. Add Employee", lines[9].trim());
		assertEquals("2. Remove Employee", lines[10].trim());
		assertEquals("Choose an option: Invalid option. Returning to menu.", lines[11].trim());

	}

	@Test
	void testManageEmployeesAddEmployee_1() {
		String input = "2 " + "\n1" + "\nemp" + "\n5\n";
		InputHandler.getInstance()
				.setScanner(setInput(input));

		Manager manager = new Manager("test");
		manager.displayMenu();

		String[] lines = getOutput().split("\n");

		assertEquals("Choose an option: Enter new username:Username already exists!", lines[11].trim());

	}

	@Test
	void testManageEmployeesAddEmployee_2() {
		String input = "2 " + "\n1" + "\nemp2" + "\nabc" + "\nacb" + "\nabc" + "\nabc" + "\n5\n";
		InputHandler.getInstance()
				.setScanner(setInput(input));
		assertNull(UserDatabase.getInstance().findUser("emp2"));

		Manager manager = new Manager("test");
		manager.displayMenu();

		String[] lines = getOutput().split("\n");

		assertEquals(
				"Choose an option: Enter new username:Enter PasswordRe-enter PasswordPasswords do not match. Try again.",
				lines[11].trim());
		assertEquals("Enter PasswordRe-enter PasswordUser added successfully!", lines[12].trim());
		assertNotNull(UserDatabase.getInstance().findUser("emp2"));

	}

	@Test
	void testManageEmployeesRemoveEmployee_1() {
		String input = "2 " + "\n2" + "\nemp4" + "\n5\n";
		InputHandler.getInstance()
				.setScanner(setInput(input));

		Manager manager = new Manager("test");
		manager.displayMenu();

		String[] lines = getOutput().split("\n");
		assertEquals("Choose an option: Enter username of employee to remove: Invalid username.", lines[11].trim());

	}

	@Test
	void testManageEmployeesRemoveEmployee_2() {
		String input = "2 " + "\n2" + "\nemp" + "\n5\n"; // default emp account
		InputHandler.getInstance()
				.setScanner(setInput(input));
		assertNotNull(UserDatabase.getInstance().findUser("emp"));

		Manager manager = new Manager("test");
		manager.displayMenu();
		assertNull(UserDatabase.getInstance().findUser("emp"));
		String[] lines = getOutput().split("\n");

		assertEquals("Choose an option: Enter username of employee to remove: Employee removed successfully",
				lines[11].trim());

	}

	@Test
	void testInvalid() {
		String input = "7 " + "\n5\n";
		InputHandler.getInstance()
				.setScanner(setInput(input));

		Manager manager = new Manager("test");
		manager.displayMenu();
		String[] lines = getOutput().split("\n");
		assertEquals("Choose an option: Invalid choice, please try again.", lines[8].trim());

	}

	@Test
	void testClientMenu() {
		String input = "6 " + "\n8\n";
		InputHandler.getInstance()
				.setScanner(setInput(input));
		Manager manager = new Manager("test");
		assertDoesNotThrow(() -> manager.displayMenu());
		String[] lines = getOutput().split("\n");
		assertEquals("--- Client Menu ---", lines[9].trim());
	}

	@Test
	void testToString() {
		assertEquals("Manager", new Manager("test").toString());
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
