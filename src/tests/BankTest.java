package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import banking.Account;
import banking.Bank;
import banking.Checking;
import banking.LoanApproved;
import banking.User;
import banking.UserDatabase;

class BankTest {
	private Bank bank;
	private static Account a;
	private static User u;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		bank = Bank.getInstance();
		u = UserDatabase.getInstance().findUser("customer");
		a = new Account(50, Checking.getInstance(), u);
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
	void testRemoveAccountById() {
		bank.addAccount(a);
		
		assertTrue(bank.hasAccount(u));
		Account removedA = bank.removeAccount(a.toString());

		assertSame(removedA, a);
		assertFalse(bank.hasAccount(u));
		
	}
	@Test
	void testAddAccount() {
		bank.addAccount(a);

		assertTrue(bank.hasAccount(u));
	}

	@Test
	void testRemoveAccount() {

		bank.removeAccount(a);

		assertFalse(bank.hasAccount(u));
	}

}
