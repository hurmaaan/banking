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

	@Test
	void test() {

		String input = "5\n";
		InputHandler.getInstance().setScanner(setInput(input));
		new Manager().displayMenu();

		String[] lines = getOutput().split("\n");
		assertEquals("Logging out...", lines[7].trim());

	}

	private static InputStream setInput(String input) {
		InputStream in = new ByteArrayInputStream(input.getBytes());
		return in;

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
