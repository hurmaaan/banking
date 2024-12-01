package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import banking.Account;
import banking.Bank;
import banking.Checking;
import banking.Client;
import banking.LoanApproved;
import banking.Savings;
import banking.User;
import banking.UserDatabase;

class BankTest {
	private Bank bank;
	private static Account acToRemoveAndAdd;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		User u = UserDatabase.getInstance().findUser("customer");
		acToRemoveAndAdd = new Account(50, Checking.getInstance(), u);

	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		bank = Bank.getInstance();
		setOutput();

	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testSingletonInstance() {

		Bank anotherInstance = Bank.getInstance();
		assertSame(bank, anotherInstance);
	}

	@Test
	void testAddAccount() {
		User u = UserDatabase.getInstance().findUser("customer");

		bank.addAccount(acToRemoveAndAdd);

		assertTrue(bank.hasAccount(u));
	}

	@Test
	void testRemoveAccount() {
		User u = UserDatabase.getInstance().findUser("customer");

		bank.removeAccount(acToRemoveAndAdd);

		assertFalse(bank.hasAccount(u));
	}

	@Test
	void testRemoveAccountById() {
		User u = UserDatabase.getInstance().findUser("emp");

		Account a = new Account(50, Checking.getInstance(), u);
		bank.addAccount(a);

		assertTrue(bank.hasAccount(u));
		Account removedA = bank.removeAccount(a.toString());

		assertSame(removedA, a);
		assertFalse(bank.hasAccount(u));
	}

	@Test
	void testRemoveInvalidAccount() {
		Account a = bank.removeAccount("60");
		assertEquals(null, a);
	}

	@Test
	void testCloseAccount() {
		// all the lower functions have already been tested
		User testUserCloseAccount = new User("testClose", "test123", new Client("test"));
		UserDatabase.getInstance().addUser(testUserCloseAccount);
		Account ac = new Account(0, Savings.getInstance(), testUserCloseAccount);
		bank.addAccount(ac);
		bank.closeAccount(ac.toString());
		String output = getOutput();
		assertEquals("Account Closed Successfully!", output.trim());
		assertFalse(bank.hasAccount(testUserCloseAccount));

	}

	@Test
	void testCloseInvalidAccount() {
		bank.closeAccount("30");
		String output = getOutput();
		assertEquals("Account Not Found", output.trim());
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
