package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import banking.Savings;

class SavingsTest {

	private Savings savingsAccount;

	@BeforeEach
	void setUp() {
		savingsAccount = Savings.getInstance();
	}

	@Test
	void testSingletonInstance() {
		Savings anotherInstance = Savings.getInstance();
		assertSame(savingsAccount, anotherInstance);
	}

	@Test
	void testToString() {
		assertEquals("Savings", savingsAccount.toString());
	}

	@Test
	void testCalculateMonthlyPayment() {
		savingsAccount.setInterestRate(0.1);
		double expectedPayment = 87.92;
		double actualPayment = savingsAccount.calculateMonthlyPayment(12, 1000);
		assertEquals(expectedPayment, actualPayment, 0.01);
	}

	@Test
	void testCalculateMonthlyPaymentWithDifferentRates() {
		savingsAccount.setInterestRate(0.12);
		double expectedPayment = 88.85;
		double actualPayment = savingsAccount.calculateMonthlyPayment(12, 1000);
		assertEquals(expectedPayment, actualPayment, 0.01);
	}

}
