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
import banking.CmdOpenAccount;
import banking.RecordedCommand;
import banking.User;
import banking.UserDatabase;

class CmdOpenAccountTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {

	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		setOutput();
		Account.reset();
		UserDatabase.reset();
		Bank.reset();
	}

	@AfterEach
	void tearDown() throws Exception {
	
	}

	@Test
	void testExecuteUndoRedoSavings() {
		User u = UserDatabase.getInstance().findUser("customer");
		assertFalse(Bank.getInstance().hasAccount(u));
		new CmdOpenAccount().execute(new String[] { "5.0", "Savings", "customer" });
		assertTrue(Bank.getInstance().hasAccount(u));
		RecordedCommand.undoOneCommand();
		assertFalse(Bank.getInstance().hasAccount(u));
		RecordedCommand.redoOneCommand();
		assertTrue(Bank.getInstance().hasAccount(u));
		String[] outputLines = getOutput().split("\n");
		assertEquals("Account Opened Successfully!", outputLines[0].trim());
		assertEquals("Account ID: 109", outputLines[1].trim());
		assertEquals("Balance: 5.0", outputLines[2].trim());
		assertEquals("Type: Savings", outputLines[3].trim());
		assertEquals("------", outputLines[4].trim());

		assertEquals("Undo Successful. Closed Bank Account 109", outputLines[5].trim());
		assertEquals("Redo Successful. ReOpened Bank Account 109", outputLines[6].trim());

	}

	@Test
	void testExecuteUndoRedoChecking() {
		User u = UserDatabase.getInstance().findUser("customer");
		assertFalse(Bank.getInstance().hasAccount(u));
		new CmdOpenAccount().execute(new String[] { "59.0", "Checking", "customer" });
		assertTrue(Bank.getInstance().hasAccount(u));
		RecordedCommand.undoOneCommand();
		assertFalse(Bank.getInstance().hasAccount(u));
		RecordedCommand.redoOneCommand();
		assertTrue(Bank.getInstance().hasAccount(u));
		String[] outputLines = getOutput().split("\n");
		assertEquals("Account Opened Successfully!", outputLines[0].trim());
		assertEquals("Account ID: 109", outputLines[1].trim());
		assertEquals("Balance: 59.0", outputLines[2].trim());
		assertEquals("Type: Checking", outputLines[3].trim());
		assertEquals("------", outputLines[4].trim());

		assertEquals("Undo Successful. Closed Bank Account 109", outputLines[5].trim());
		assertEquals("Redo Successful. ReOpened Bank Account 109", outputLines[6].trim());

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
