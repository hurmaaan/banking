package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import banking.Account;
import banking.Bank;
import banking.Checking;
import banking.Client;
import banking.RecordedCommand;
import banking.Savings;
import banking.User;
import banking.UserDatabase;

class AccountTest {

	private static Account acc;
	private static User user;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		Account.reset();
		Bank.reset();
		UserDatabase.reset();
		RecordedCommand.reset();
		user = UserDatabase.getInstance().findUser("emp");
		acc = new Account(300, Savings.getInstance(), user);
		Bank.getInstance().addAccount(acc);
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
	void testCloseError() {
		acc.close();
		String output = getOutput();
		assertEquals("Cannot close account with remaining balance. Please withdraw first!", output.trim());
	}

	@Test
	void testCloseSuccess() {
		Account acc = new Account(0, Checking.getInstance(), user);
		acc.close();
		String output = getOutput();
		assertEquals("Account Closed Successfully!", output.trim());

	}

	@Test
	void testPrintDetails() {
		acc.printDetails();
		String[] output = getOutput().split("\n");
		assertEquals("Account ID: 109", output[0].trim());
		assertEquals("Balance: 300.0", output[1].trim());
		assertEquals("Type: Savings", output[2].trim());
		assertEquals("------", output[3].trim());
	}

	@Test
	void testVoidAccountBelongsToUser() {
		ArrayList<Account> accounts = new ArrayList<>();
		accounts.add(acc);

		assertSame(acc, Account.accountBelongsToUser(accounts, user.toString(), acc.toString()));

	}

	@Test
	void testVoidAccountNotBelongsToUser() {
		ArrayList<Account> accounts = new ArrayList<>();
		User testUser = new User("test", "test", new Client("test"));
		accounts.add(acc);
		assertSame(null, Account.accountBelongsToUser(accounts, testUser.toString(), acc.toString()));
		assertSame(null, Account.accountBelongsToUser(accounts, testUser.toString(), "120"));

	}

	@Test
	void testListAccounts() {
		ArrayList<Account> accounts = new ArrayList<>();
		accounts.add(acc);
		User testUser = new User("test", "test", new Client("test"));
		Account a = new Account(5, Savings.getInstance(), testUser);
		accounts.add(a);
		Account.listAccounts(accounts, user);
		String[] output = getOutput().split("\n");
		assertEquals("Account ID: 109", output[0].trim());
		assertEquals("Balance: 300.0", output[1].trim());
		assertEquals("Type: Savings", output[2].trim());
		assertEquals("------", output[3].trim());

	}

	@Test
	void testListAccountsNoAccount() {
		ArrayList<Account> accounts = new ArrayList<>();
		accounts.add(acc);
		User testUser = new User("test", "test", new Client("test"));

		Account.listAccounts(accounts, testUser);
		String output = getOutput();

		assertEquals("This user has no accounts", output.trim());

	}

	@Test

	void testCalculateMonthlyPayment() {
		Savings.getInstance().setInterestRate(0.1);
		double expectedPayment = 87.92;
		double actualPayment = acc.calculateMonthlyPayment(12, 1000);
		assertEquals(expectedPayment, actualPayment, 0.01);
	}

	@Test
	void testToString() {
		assertEquals("109", acc.toString()); // 109 is always the id of the first account
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
