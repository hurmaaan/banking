package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import banking.Account;
import banking.Bank;
import banking.Checking;
import banking.Client;
import banking.InputHandler;
import banking.LoanApproved;
import banking.Savings;
import banking.Transfer;
import banking.User;
import banking.UserDatabase;

class BankTest {
	private Bank bank;
	private static Account acToRemoveAndAdd;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		Bank.reset();
		UserDatabase.reset();
		Account.reset();
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		Bank.reset();
		UserDatabase.reset();
		Account.reset();
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
		acToRemoveAndAdd = new Account(50, Checking.getInstance(), u);

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

	@Test
	void testListAccounts() {
		User user = new User("testListBank", "123", new Client("testListBank"));
		Account a = new Account(5, Savings.getInstance(), user);
		bank.addAccount(a);
		bank.listAccounts(user);
		String[] output = getOutput().split("\n");
		assertEquals("Account ID: " + a.toString(), output[0].trim());
		assertEquals("Balance: 5.0", output[1].trim());
		assertEquals("Type: Savings", output[2].trim());
		assertEquals("------", output[3].trim());
	}

	@Test
	void testUserHasAccount() {
		User user = new User("testListAccountExist", "123", new Client("testListAccountExist"));
		Account a = new Account(6, Savings.getInstance(), user);
		bank.addAccount(a);
		assertSame(a, bank.userHasAccount(user.toString(), a.toString()));
	}

	@Test
	void testUserHasAccountNull() {
		User user = new User("testListAccountExistNull", "123", new Client("testListAccountExistNull"));
		Account a = new Account(6, Savings.getInstance(), user);

		assertEquals(null, bank.userHasAccount(user.toString(), a.toString()));
	}

	@Test
	void testTransferInvalid_1() {
		bank.transfer(new Transfer(0, false, "201"), "2098", "customer", "109", 0);
		assertEquals("Invalid Sender Account ID", getOutput().trim());

	}

	@Test
	void testTransferInvalid_2() {
		User user = UserDatabase.getInstance().findUser("emp");
		Account a = new Account(0, Savings.getInstance(), user);
		bank.addAccount(a);
		bank.transfer(new Transfer(0, false, "201"), a.toString(), "emp", "1009", 0);

		assertEquals("Invalid receiver account", getOutput().trim());

	}

	@Test
	void testTransferValid() {
		User user = UserDatabase.getInstance().findUser("emp");
		Account r = new Account(0, Savings.getInstance(), user);
		Account s = new Account(20, Checking.getInstance(), user);

		bank.addAccount(s);
		bank.addAccount(r);
		bank.transfer(new Transfer(0, false, "x"), s.toString(), "emp", r.toString(), 10);
		Bank.reset();
		assertEquals("Transfer Successfull!", getOutput().trim());

	}

	@Test

	void testListTransactionsInvalid() {
		bank.listTransactions("xyz", "201");
		assertEquals("Invalid account Id", getOutput().trim());

	}

	@Test
	void testListTransactionsValid() {
		User u = UserDatabase.getInstance().findUser("customer");
		Account a = new Account(0, Savings.getInstance(), u);
		bank.addAccount(a);
		bank.listTransactions("customer", a.toString());
		assertNotEquals("Invalid account Id", getOutput().trim());

	}

	@Test
	void testStart_1() {
		String input = "1 " + "\ns" + "\ns" + "\n2\n";
		InputHandler.getInstance()
				.setScanner(setInput(input));
		Bank.getInstance().start();
		String[] lines = getOutput().split("\n");
		assertEquals("----Welcome to the Banking Management System----", lines[0].trim());
		assertEquals("Choose an option:", lines[1].trim());
		assertEquals("1. Login", lines[2].trim());
		assertEquals("2. Exit", lines[3].trim());
		assertEquals("Choose an option: Enter username: Enter password: User does not exist.", lines[4].trim());
		assertEquals("----Welcome to the Banking Management System----", lines[5].trim());
		assertEquals("Choose an option:", lines[6].trim());
		assertEquals("1. Login", lines[7].trim());
		assertEquals("2. Exit", lines[8].trim());
		assertEquals("Choose an option: Exiting....", lines[9].trim());

	}

	@Test
	void testStart_2() {
		String input = "3 " + "\n2\n";
		InputHandler.getInstance()
				.setScanner(setInput(input));
		Bank.getInstance().start();
		String[] lines = getOutput().split("\n");
		assertEquals("----Welcome to the Banking Management System----", lines[0].trim());
		assertEquals("Choose an option:", lines[1].trim());
		assertEquals("1. Login", lines[2].trim());
		assertEquals("2. Exit", lines[3].trim());

	}

	@Test
	void testStart_3() {
		String input = "1 " + "\ncustomer" + "\ncustomer123" + "\n8" + "\n2\n";
		InputHandler.getInstance()
				.setScanner(setInput(input));
		Bank.getInstance().start();
		String[] lines = getOutput().split("\n");
		assertEquals("----Welcome to the Banking Management System----", lines[0].trim());
		assertEquals("Choose an option:", lines[1].trim());
		assertEquals("1. Login", lines[2].trim());
		assertEquals("2. Exit", lines[3].trim());
		assertEquals("Choose an option: Enter username: Enter password: Login successful! Welcome, Client.", lines[4].trim());

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

	static InputStream setInput(String input) {
		byte[] bytes = input.replace("\n", System.lineSeparator()).getBytes();
		return new ByteArrayInputStream(bytes);
	}

}
