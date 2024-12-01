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

//	@Test
//	void testLogOut() {
//
//		String input = "5\n";
//		InputHandler.getInstance().setScanner(setInput(input));
//		new Manager().displayMenu();
//
//		String[] lines = getOutput().split("\n");
//		assertEquals("Logging out...", lines[8].trim());
//		InputHandler.getInstance().close();
//	}

	@Test
	void testSetInterestRates() {

		String input = "1\n 0.01\n0.03\n";
		InputHandler.getInstance()
		.setScanner(setInput(input));
		User manager = new Manager();
		
		manager.displayMenu();

		String[] lines = getOutput().split("\n");
		assertEquals("This command cannot be undone!", lines[8].trim());

		assertEquals("Set Interest Rates for Account Types:", lines[9].trim());
		assertEquals("Enter interest rate for Savings Account (e.g., 0.03 for 3%): ", lines[10].trim());
		assertEquals("Enter interest rate forChecking Account (e.g., 0.01 for 1%): ", lines[11].trim());

	}

	// static InputStream setInput(String input) {
	// String[] lines = input.split("\n");

	// for (String s : lines) {

	// }

	// InputStream in = new ByteArrayInputStream(input.getBytes());
	// return in;

	// }

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
