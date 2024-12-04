package tests;

import static org.junit.jupiter.api.Assertions.*;

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
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testToString_1() {
		Transfer t = new Transfer(20, false, "201");
		assertEquals("Received 20.0 from 201",t.toString());
	}

	@Test
	void testToString_2() {
		Transfer t = new Transfer(20, true, "201");
		assertEquals("Transferred 20.0 to 201",t.toString());
	}

}
