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

import banking.Client;
import banking.Employee;
import banking.InputHandler;
import banking.Manager;
import banking.User;
import banking.UserDatabase;
import banking.Authenticator;

class AuthenticatorTest {
	private static User user1 = new User("test", "test123", new Client("test"));
	private static User user2 = new User("test2", "test123", new Manager("test2"));
	private static User user3 = new User("test3", "test123", new Employee("test3"));

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
	void authenticateClient() {
		String input = "test\ntest123";
		InputHandler.getInstance().setScanner(setInput(input));
		UserDatabase userDatabase = UserDatabase.getInstance();
		userDatabase.addUser(user1);
		Authenticator authenticator = new Authenticator(userDatabase);
		User user = authenticator.authenticate();
		String[] outputLines = getOutput().split("\n");

		assertEquals("Enter username: Enter password: Login successful! Welcome, Client.", outputLines[0].trim());
		assertSame(user1, user);
	}

	@Test
	void authenticateManager() {
		String input = "test2\ntest123";
		InputHandler.getInstance().setScanner(setInput(input));
		UserDatabase userDatabase = UserDatabase.getInstance();
		userDatabase.addUser(user2);
		Authenticator authenticator = new Authenticator(userDatabase);
		User user = authenticator.authenticate();
		String[] outputLines = getOutput().split("\n");

		assertEquals("Enter username: Enter password: Login successful! Welcome, Manager.", outputLines[0].trim());
		assertSame(user2, user);
	}

	@Test
	void authenticateEmployee() {
		String input = "test3\ntest123";
		InputHandler.getInstance().setScanner(setInput(input));
		UserDatabase userDatabase = UserDatabase.getInstance();
		userDatabase.addUser(user3);
		Authenticator authenticator = new Authenticator(userDatabase);
		User user = authenticator.authenticate();
		String[] outputLines = getOutput().split("\n");

		assertEquals("Enter username: Enter password: Login successful! Welcome, Employee.", outputLines[0].trim());
		assertSame(user3, user);
	}

	@Test
	void testUserDoesNotExist() {
		String input = "test4\ntest123";
		InputHandler.getInstance().setScanner(setInput(input));
		UserDatabase userDatabase = UserDatabase.getInstance();
		userDatabase.addUser(user3);
		Authenticator authenticator = new Authenticator(userDatabase);
		User user = authenticator.authenticate();
		String[] outputLines = getOutput().split("\n");

		assertEquals("Enter username: Enter password: User does not exist.", outputLines[0].trim());
		assertSame(null, user);
	}

	@Test
	void testInvalidPassword() {
		String input = "test3\ntest125";
		InputHandler.getInstance().setScanner(setInput(input));
		UserDatabase userDatabase = UserDatabase.getInstance();
		userDatabase.addUser(user3);
		Authenticator authenticator = new Authenticator(userDatabase);
		User user = authenticator.authenticate();
		String[] outputLines = getOutput().split("\n");

		assertEquals("Enter username: Enter password: Login failed! Invalid password.", outputLines[0].trim());
		assertSame(null, user);
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
