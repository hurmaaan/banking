package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import banking.RecordedCommand;

class RecordedCommandTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		RecordedCommand.reset();
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
	void testUndoEmpty() {
		RecordedCommand.undoOneCommand();
		String expected = "Nothing to undo.".trim();

		String actual = getOutput().trim();
		assertEquals(expected, actual);

	}

	@Test
	void testRedoEmpty() {
		RecordedCommand.redoOneCommand();
		String expected = "Nothing to redo.".trim();

		String actual = getOutput().trim();
		assertEquals(expected, actual);

	}

	@Test
	void testUndoRedo() {
		class commandStub extends RecordedCommand {

			@Override
			public void execute(String[] cmdParts) {
				addUndoCommand(this);
				clearRedoList();
				System.out.println("executed.");
			}

			@Override
			public void undoMe() {
				super.addRedoCommand(this);
				System.out.println("undone.");
			}

			@Override
			public void redoMe() {
				super.addUndoCommand(this);
				System.out.println("redone.");
			}

		}
		new commandStub().execute(null);

		RecordedCommand.undoOneCommand();

		RecordedCommand.redoOneCommand();
		RecordedCommand.reset();
		RecordedCommand.undoOneCommand();

		RecordedCommand.redoOneCommand();

		String[] actual = getOutput().split("\n");
		assertEquals(actual[0].trim(), "executed.");
		assertEquals(actual[1].trim(), "undone.");
		assertEquals(actual[2].trim(), "redone.");
		assertEquals(actual[3].trim(), "Nothing to undo.");
		assertEquals(actual[4].trim(), "Nothing to redo.");

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
