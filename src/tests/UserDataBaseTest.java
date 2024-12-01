package tests;

import static org.junit.jupiter.api.Assertions.*;

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

	@Test
	void testAddUser() {
		UserDatabase database = UserDatabase.getInstance();

		assertDoesNotThrow(() -> database.addUser(user3));

	}

	@Test
	void testFindUser() {
		UserDatabase database = UserDatabase.getInstance();
		database.addUser(user1);
		assertEquals(user1, database.findUser("test"));

	}

	@Test
	void removeUser() {
		UserDatabase database = UserDatabase.getInstance();
		database.addUser(user3);
		assertEquals(true, database.removeUser(user3));

	}

	@Test

	void removeUserFalse() {
		UserDatabase database = UserDatabase.getInstance();
		assertEquals(false, database.removeUser(user2));

	}

}
