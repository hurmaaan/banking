package tests;

import banking.InputHandler;
import banking.Savings;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.junit.jupiter.api.Test;

class InputHandlerTest {

	@Test
	void testGetLine() {

		InputHandler inputHandler = InputHandler.getInstance();
		inputHandler.setScanner(setInput("Hello, World!\n"));
		String output = inputHandler.getNextLine();
		inputHandler.close();
		assertEquals("Hello, World!", output);

	}

	@Test
	void testGetDouble() {

		InputHandler inputHandler = InputHandler.getInstance();
		inputHandler.setScanner(setInput("2.3 \n"));
		double output = inputHandler.getNextDouble();
		inputHandler.close();
		assertEquals(2.3, output);

	}

	@Test
	void testGetInt() {

		InputHandler inputHandler = InputHandler.getInstance();
		inputHandler.setScanner(setInput("2 \n"));
		int output = inputHandler.getNextInt();
		inputHandler.close();
		assertEquals(2, output);

	}

	@Test
	void testSingletonInstance() {
		InputHandler instance = InputHandler.getInstance();
		InputHandler anotherInstance = InputHandler.getInstance();
		assertSame(instance, anotherInstance);
	}

	static InputStream setInput(String input) {
		return new ByteArrayInputStream(input.getBytes());

	}

}
