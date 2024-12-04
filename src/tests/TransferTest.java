package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import banking.Transfer;

class TransferTest {

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
	void testExecuteIntegration() {

		assertDoesNotThrow(() -> new Transfer(0, false, "x").execute(new String[] { "x", "y" }));
		assertEquals("Invalid Sender Account ID", getOutput().trim());
	}

	@Test
	void testToString_1() {
		Transfer t = new Transfer(20, false, "201");
		assertEquals("Received 20.0 from 201", t.toString());
	}

	@Test
	void testToString_2() {
		Transfer t = new Transfer(20, true, "201");
		assertEquals("Transferred 20.0 to 201", t.toString());
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
