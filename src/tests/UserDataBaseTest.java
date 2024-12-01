package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import banking.Client;
import banking.Employee;
import banking.Manager;
import banking.User;
import banking.UserDatabase;

class UserDataBaseTest {
	private User user1 = new User("test", "test123", new Client("test"));
	private User user2 = new User("test2", "test123", new Manager());
	private User user3 = new User("test3", "test123", new Employee());

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testAddUser() {
		UserDatabase database = UserDatabase.getInstance();

		User user3 = new User("test3", "test123", new Employee());

		assertDoesNotThrow(() -> database.addUser(user3));

	}

	@Test
	void testFindUser() {
		UserDatabase database = UserDatabase.getInstance();
		assertEquals(user3, database.findUser("test3"));

	}

	@Test
	void removeUser() {
		UserDatabase database = UserDatabase.getInstance();
		assertEquals(true, database.removeUser(user3));

	}

	@Test

	void removeUserFalse() {
		UserDatabase database = UserDatabase.getInstance();
		assertEquals(false, database.removeUser(user2));

	}

}
