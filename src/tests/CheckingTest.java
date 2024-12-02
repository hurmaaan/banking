package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import banking.Checking;

class CheckingTest {

	private Checking checkingAccount;

	@BeforeEach
	void setUp() {
		checkingAccount = Checking.getInstance();
	}

	@Test
	void testSingletonInstance() {
		Checking anotherInstance = Checking.getInstance();
		assertSame(checkingAccount, anotherInstance);
	}

	@Test
	void testToString() {
		assertEquals("Checking", checkingAccount.toString());
	}

	@Test
	void testCalculateMonthlyPayment() {
		checkingAccount.setInterestRate(0.1);
		double expectedPayment = 87.92;
		double actualPayment = checkingAccount.calculateMonthlyPayment(12, 1000);
		assertEquals(expectedPayment, actualPayment, 0.01);
	}

	@Test
	void testCalculateMonthlyPaymentWithDifferentRates() {
		checkingAccount.setInterestRate(0.12);
		double expectedPayment = 88.85;
		double actualPayment = checkingAccount.calculateMonthlyPayment(12, 1000);
		assertEquals(expectedPayment, actualPayment, 0.01);
	}

}
