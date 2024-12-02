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
import banking.CmdRegisterUser;
import banking.RecordedCommand;
import banking.User;
import banking.UserDatabase;

class CmdRegisterUserTest {

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
	void testExecuteUndoRedoClient() {
		new CmdRegisterUser().execute(new String[] { "usernam1", "password1", "client" });
		User addedUser = UserDatabase.getInstance().findUser("usernam1");
		assertNotNull(addedUser);

		RecordedCommand.undoOneCommand();
		assertNull(UserDatabase.getInstance().findUser("usernam1"));
		RecordedCommand.redoOneCommand();
		assertSame(addedUser, UserDatabase.getInstance().findUser("usernam1"));

		String[] outputLines = getOutput().split("\n");
		assertEquals("User added successfully!", outputLines[0].trim());
		assertEquals("Undo successful usernam1 removed.", outputLines[1].trim());
		assertEquals("Redo successful. usernam1 readded", outputLines[2].trim());

	}

	@Test
	void testExecuteUndoRedoEmployee() {
		new CmdRegisterUser().execute(new String[] { "usernam2", "password1", "employee" });
		User addedUser = UserDatabase.getInstance().findUser("usernam2");
		assertNotNull(addedUser);

		RecordedCommand.undoOneCommand();
		assertNull(UserDatabase.getInstance().findUser("usernam2"));
		RecordedCommand.redoOneCommand();
		assertSame(addedUser, UserDatabase.getInstance().findUser("usernam2"));

		String[] outputLines = getOutput().split("\n");
		assertEquals("User added successfully!", outputLines[0].trim());
		assertEquals("Undo successful usernam2 removed.", outputLines[1].trim());
		assertEquals("Redo successful. usernam2 readded", outputLines[2].trim());

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
