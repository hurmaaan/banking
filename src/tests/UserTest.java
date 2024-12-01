package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;

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

class UserTest {
	private static User user1 = new User("test", "test123", new Client("test"));
	private static User user2 = new User("test2", "test123", new Manager());
	private static User user3 = new User("test3", "test123", new Employee());

	private static ArrayList<User> users = new ArrayList<>();

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		users.add(user1);
		users.add(user2);
		users.add(user3);

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
	void testToString() {
		assertEquals("test", user1.toString());
	}

	@Test
	void testisPasswordCorrect() {
		assertEquals(true, user1.isPasswordCorrect("test123"));
	}

	@Test
	void testisPasswordCorrectFalse() {
		assertEquals(false, user1.isPasswordCorrect("test3"));
	}

	@Test
	void testGetRoleClient() {
		assertEquals("Client", user1.getRole());
	}

	@Test
	void testGetRoleEmployee() {
		assertEquals("Employee", user3.getRole());
	}

	@Test
	void testGetRoleManager() {
		assertEquals("Manager", user2.getRole());
	}

	@Test
	void testUserExists() {
		assertEquals(user3, User.userExists("test3", users));
	}

	@Test
	void testUserDoesNotExist() {
		assertEquals(null, User.userExists("test5", users));
	}

	@Test
	void testDisplayClientMenu() {
		InputHandler.getInstance().close();
		InputHandler.getInstance(setInput("8\n"));
		
		user1.menu();

		String output = getOutput();
		String[] lines = output.split("\n");
		assertEquals("--- Client Menu ---", lines[1].trim());
		assertEquals("1. View Account Details", lines[2].trim());
		assertEquals("2. Deposit", lines[3].trim());
		assertEquals("3. Withdraw", lines[4].trim());
		assertEquals("4. Transfer", lines[5].trim());
		assertEquals("5. Apply for Loan", lines[6].trim());
		assertEquals("6. Make Monthly Loan Payment", lines[7].trim());
		assertEquals("7. List transaction history", lines[8].trim());
		assertEquals("8. Logout", lines[9].trim());
		

	}

	
	@Test
	void testDisplayEmployeeMenu() {

	}

	@Test
	void testDisplayManagerMenu() {

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

	private static InputStream setInput(String input) {
		InputStream in = new ByteArrayInputStream(input.getBytes());
		return in;

	}

}
