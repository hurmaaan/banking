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
import banking.Deposit;
import banking.RecordedCommand;
import banking.RepayLoan;
import banking.Savings;
import banking.Transfer;
import banking.User;
import banking.UserDatabase;
import banking.Withdrawal;

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

	@Test
	void testDeposit_ListTransactions() {
		Account ac = new Account(20, Savings.getInstance(), user);
		ac.deposit(new Deposit(20), 20.0);
		ac.listTransactions();
		String[] output = getOutput().split("\n");
		assertEquals("Deposit Successful!", output[0].trim());
		assertEquals("New Balance: 40.0", output[1].trim());

		assertEquals("Account ID: " + ac.toString(), output[2].trim());
		assertEquals("Balance: 40.0", output[3].trim());
		assertEquals("Type: Savings", output[4].trim());
		assertEquals("------", output[5].trim());
		assertEquals("Deposited 20.0", output[6].trim());

	}

	@Test
	void withdrawalInvalid() {
		Account a = new Account(20, Checking.getInstance(), null);
		a.withdraw(new Withdrawal(30), 30);
		assertEquals("Not Enough Balance", getOutput().trim());
	}

	@Test
	void withdrawalValid() {
		Account a = new Account(330, Checking.getInstance(), null);
		a.withdraw(new Withdrawal(30), 30);

		assertEquals("Withdrawal Accepted!\n New Balance: 300.0", getOutput().trim());

	}

	@Test
	void transferInValid() {

		Account s = new Account(0, Checking.getInstance(), user);
		Account r = new Account(0, Checking.getInstance(), user);
		Account.transfer(new Transfer(2, false, s.toString()), r, s, 20);
		assertEquals("Insufficient Balance", getOutput().trim());

	}

	@Test
	void transferValid() {

		Account s = new Account(20, Checking.getInstance(), user);
		Account r = new Account(0, Checking.getInstance(), user);
		Account.transfer(new Transfer(2, false, s.toString()), r, s, 20);
		assertEquals("Transfer Successfull!", getOutput().trim());

	}

	@Test

	void testRepayLoan() {
		RepayLoan r = new RepayLoan(20000, "2");
		Account a = new Account(0, Checking.getInstance(), null);
		a.repayLoan(r, 20000, 20);
		assertEquals("Insufficient Balance.", getOutput().trim());

	}

	@Test
	void testRepayLoan_2() {
		RepayLoan r = new RepayLoan(200, "2");
		Account a = new Account(200, Checking.getInstance(), null);
		assertEquals(20, a.repayLoan(r, 20, 2000));
		assertEquals("Repayment Made!", getOutput().trim());

	}

	@Test
	void testRepayLoan_3() {
		RepayLoan r = new RepayLoan(200, "2");
		Account a = new Account(200, Checking.getInstance(), null);
		assertEquals(20, a.repayLoan(r, 20, 10));
		a.listTransactions();
		assertEquals("Loan repayment Adjustment: 10.0 from loanId: 2", getOutput().split("\n")[6].trim());

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
