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
import banking.CmdRemoveUser;
import banking.RecordedCommand;
import banking.User;
import banking.UserDatabase;

class CmdRemoveUserTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		RecordedCommand.reset();
		UserDatabase.reset();
		Account.reset();
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
	void testExecuteUndoRedo() {
		User existingUser = UserDatabase.getInstance().findUser("emp");// default employee account
		assertNotNull(existingUser);

		new CmdRemoveUser().execute(new String[] { "emp" });
		assertNull(UserDatabase.getInstance().findUser("emp"));
		RecordedCommand.undoOneCommand();
		assertNotNull(UserDatabase.getInstance().findUser("emp"));
		assertSame(existingUser, UserDatabase.getInstance().findUser("emp"));
		RecordedCommand.redoOneCommand();
		assertNull(UserDatabase.getInstance().findUser("emp"));

		String[] outputLines = getOutput().split("\n");
		assertEquals("Employee removed successfully", outputLines[0].trim());
		assertEquals("Undo Successful. ReAdded Employee: emp", outputLines[1].trim());
		assertEquals("Redo Successful. Removed Employee: emp", outputLines[2].trim());

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
