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
import banking.CmdCloseAccount;
import banking.RecordedCommand;
import banking.Savings;
import banking.User;
import banking.UserDatabase;

class CmdCloseAccountTest {

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
	void testExecuteRedoUndo() {
		User u = UserDatabase.getInstance().findUser("customer");
		Account ac = new Account(500, Savings.getInstance(), u);
		Bank.getInstance().addAccount(ac);
		// check if account is successfully added
		assertTrue(Bank.getInstance().hasAccount(u));
		new CmdCloseAccount().execute(new String[] { ac.toString() });
		assertFalse(Bank.getInstance().hasAccount(u));
		RecordedCommand.undoOneCommand();
		assertTrue(Bank.getInstance().hasAccount(u));
		RecordedCommand.undoOneCommand();
		RecordedCommand.redoOneCommand();
		assertFalse(Bank.getInstance().hasAccount(u));

		String[] outputLines = getOutput().split("\n");
		assertEquals("Account Closed Successfully!", outputLines[0].trim());
		assertEquals("Undo Successful. Reopened Bank Account " + ac, outputLines[1].trim());
		assertEquals("Nothing to undo.", outputLines[2].trim());
		assertEquals("Redo Successful. Reclosed Bank Account " + ac, outputLines[3].trim());

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
