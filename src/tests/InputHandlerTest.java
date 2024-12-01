package tests;

import banking.InputHandler;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.junit.jupiter.api.Test;

class InputHandlerTest {

	@Test
	void testGetLine() {

		setInput("Hello, World!\n");
		InputHandler inputHandler = InputHandler.getInstance();
		// Call the method that reads from System.in
		String output = inputHandler.getNextLine();
		inputHandler.close();
		assertEquals("Hello, World!", output);

	}

	@Test
	void testGetDouble() {

		setInput("2.3 ");
		InputHandler inputHandler = InputHandler.getInstance();
		// Call the method that reads from System.in
		double output = inputHandler.getNextDouble();
		inputHandler.close();
		assertEquals(2.3, output);

	}

	@Test
	void testGetInt() {

		setInput("2 ");
		InputHandler inputHandler = InputHandler.getInstance();
		// Call the method that reads from System.in
		int output = inputHandler.getNextInt();
		inputHandler.close();
		assertEquals(2, output);

	}

	@Test
	void testConstructorBranches() {
		InputHandler.getInstance();
		InputHandler inputHandler = InputHandler.getInstance();
		assertDoesNotThrow(() -> inputHandler.close());
	}

	void setInput(String input) {
		InputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);

	}

}
