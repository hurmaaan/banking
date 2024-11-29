package bankingTest;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import banking.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class LoanApplicationTest {

    private ByteArrayOutputStream outputStream;
    private PrintStream originalOut;

    private List<LoanApplication> loanApplications;
    private Account account;

    @BeforeEach
    public void setUp() {
        // Redirect System.out to capture output
        originalOut = System.out;
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // Initialize loan applications list and account for testing
        loanApplications = new ArrayList<>();
        User user = new User("testUser", "password", new Client("testUser"));
        account = new Account(5000, Savings.getInstance(), user);
    }

    @Test
    public void testCreateLoanApplication_Success() {
        // Create a new loan application
        LoanApplication loanApp = LoanApplication.createLoanApplication(loanApplications, account, 12, 1000);

        // Verify the loan application was successfully created
        assertNotNull(loanApp);
        assertEquals(1, loanApplications.size()); // Ensure the loan was added to the list
        assertTrue(outputStream.toString().contains("Applied Successfuly for a loan."));
    }

    @Test
    public void testCreateLoanApplication_AccountAlreadyHasPendingLoan() {
        // Create an initial loan for the account
        LoanApplication.createLoanApplication(loanApplications, account, 12, 1000);

        // Try creating another loan for the same account
        LoanApplication secondLoanApp = LoanApplication.createLoanApplication(loanApplications, account, 12, 2000);

        // Verify that the second loan application was not created
        assertNull(secondLoanApp);
        assertEquals(1, loanApplications.size());
        assertTrue(outputStream.toString().contains("This account already has a pending loan."));
    }

    @Test
    public void testApproveLoan() {
        // Create a loan application
        LoanApplication loanApp = LoanApplication.createLoanApplication(loanApplications, account, 12, 1000);

        // Approve the loan
        loanApp.approveLoan();

        // Verify that the loan's status is updated to approved (indirectly via output)
        assertTrue(outputStream.toString().contains("Loan Approved. Loan ID: " + loanApp.toString()));
    }

    @Test
    public void testRejectLoan() {
        // Create a loan application
        LoanApplication loanApp = LoanApplication.createLoanApplication(loanApplications, account, 12, 1000);

        // Reject the loan
        loanApp.rejectLoan();

        // Verify that the loan's status is updated to rejected (indirectly via output)
        assertTrue(outputStream.toString().contains("Loan Rejected. Loan ID: " + loanApp.toString()));
    }

    @Test
    public void testListPendingLoans() {
        // Create multiple loan applications
        LoanApplication loan1 = LoanApplication.createLoanApplication(loanApplications, account, 12, 1000);
        Account secondAccount = new Account(3000, Savings.getInstance(), new User("secondUser", "password", new Client("secondUser")));
        LoanApplication loan2 = LoanApplication.createLoanApplication(loanApplications, secondAccount, 24, 5000);

        // Call the listPendingLoans method
        LoanApplication.listPendingLoans(loanApplications);

        // Verify that both loans are listed as pending
        String output = outputStream.toString();
        assertTrue(output.contains("--- Pending Loan Applications ---"));
        assertTrue(output.contains("Loan ID: " + loan1.toString()));
        assertTrue(output.contains("Loan ID: " + loan2.toString()));
    }

    @Test
    public void testRepayLoan_UserHasNoAccount() {
        // Create a loan application
        LoanApplication loanApp = LoanApplication.createLoanApplication(loanApplications, account, 12, 1000);

        // Attempt to repay the loan using an invalid username
        String[] cmdParts = {loanApp.toString(), "invalidUser", "500"};
        RepayLoan repayLoan = new RepayLoan(Double.parseDouble(cmdParts[2]));
        repayLoan.execute(cmdParts);

        // Verify that repayment fails due to invalid user
        assertTrue(outputStream.toString().contains("Invalid loan id"));
    }

    @Test
    public void testRepayLoan_AmountLessThanMinimumPayment() {
        // Create a loan application
        LoanApplication loanApp = LoanApplication.createLoanApplication(loanApplications, account, 12, 1000);

        // Attempt to repay with an amount less than the minimum payment
        String[] cmdParts = {loanApp.toString(), "testUser", "10"};
        RepayLoan repayLoan = new RepayLoan(Double.parseDouble(cmdParts[2]));
        repayLoan.execute(cmdParts);

        // Verify that repayment fails due to insufficient amount
        assertTrue(outputStream.toString().contains("Amount cannot be less than monthly payment"));
    }

    @Test
    public void testRepayLoan_PartialRepayment() {
        // Create a loan application
        LoanApplication loanApp = LoanApplication.createLoanApplication(loanApplications, account, 12, 1000);

        // Repay part of the loan
        String[] cmdParts = {loanApp.toString(), "testUser", "500"};
        RepayLoan repayLoan = new RepayLoan(Double.parseDouble(cmdParts[2]));
        repayLoan.execute(cmdParts);

        // Verify that the repayment was processed (indirectly via output)
        assertTrue(outputStream.toString().contains("Partial repayment successful."));
    }

    @Test
    public void testRepayLoan_FullyRepaid() {
        // Create a loan application
        LoanApplication loanApp = LoanApplication.createLoanApplication(loanApplications, account, 12, 1000);

        // Fully repay the loan
        String[] cmdParts = {loanApp.toString(), "testUser", "1000"};
        RepayLoan repayLoan = new RepayLoan(Double.parseDouble(cmdParts[2]));
        repayLoan.execute(cmdParts);

        // Verify that the loan is fully repaid (indirectly via output)
        assertTrue(outputStream.toString().contains("Loan Fully Repaid!"));
    }

    @Test
    public void testToStringReturnsLoanId() {
        // Create a loan application and verify its string representation
        LoanApplication loanApp = LoanApplication.createLoanApplication(loanApplications, account, 12, 1000);

        // The toString() method should return the loan ID
        assertNotNull(loanApp.toString());
        assertFalse(loanApp.toString().isEmpty());
    }
}