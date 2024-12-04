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
import banking.Deposit;
import banking.Savings;
import banking.User;
import banking.UserDatabase;

class DepositTest {

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
	void testToString() {
		Deposit d = new Deposit(10);
		assertEquals("Deposited 10.0", d.toString());
	}

	@Test
	void testExecuteInvalid() {
		new Deposit(20).execute(new String[] { "1", "customer" });
		assertEquals("Invalid account id", getOutput().trim());
	}

	@Test
	void testExecuteValid() {
		User u = UserDatabase.getInstance().findUser("customer");
		Account a = new Account(0, Savings.getInstance(), u);
		Bank.getInstance().addAccount(a);
		new Deposit(20).execute(new String[] { a.toString(), "customer" });
		assertEquals("Deposit Successful!\n New Balance: 20.0", getOutput().trim());

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
