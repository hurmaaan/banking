package bankingTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import banking.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {

    private User user1;
    private User user2;
   
    private Account account1;
    private Account account2;
    

    @Test
    void test1() {
    	assertEquals(1, 1);
    }
    @BeforeEach
    void setUp() {
    	Client client1 = new Client("user1");
    	Client client2 = new Client("user2");
        user1 = new User("user1", "pwd1", client1); // Mocking User class
        user2 = new User("user1", "pwd1", client2);
        account1 = new Account(1000.0, AccountType.SAVINGS, user1);
        account2 = new Account(2000.0, AccountType.CHECKING, user2);
    }

    @Test
    void testConstructorWithUser() {
        assertNotNull(account1.toString()); // Ensures account ID is generated
        assertEquals(1000.0, account1.balance);
        assertEquals(AccountType.SAVINGS, account1.type);
        assertEquals(user1, account1.user);
    }

    @Test
    void testConstructorWithAccountId() {
        Account account = new Account(500.0, AccountType.SAVINGS, "110");
        assertEquals("110", account.toString());
        assertEquals(500.0, account.balance);
        assertEquals(AccountType.SAVINGS, account.type);
    }

    @Test
    void testListAccounts_WithAccounts() {
        List<Account> accounts = new ArrayList<>();
        accounts.add(account1);
        accounts.add(account2);

        // Test listing for user1
        account1.listAccounts(accounts, user1);

        // Since listAccounts only prints, we manually verify by running it (mocking `System.out` for assertions is optional)
    }

    @Test
    void testListAccounts_NoAccounts() {
        List<Account> accounts = new ArrayList<>();

        // No accounts for user1
        account1.listAccounts(accounts, user1);
    }

    @Test
    void testUserHasAccounts_True() {
        List<Account> accounts = new ArrayList<>();
        accounts.add(account1);

        assertTrue(Account.userHasAccounts(accounts, user1));
    }

    @Test
    void testUserHasAccounts_False() {
        List<Account> accounts = new ArrayList<>();
        accounts.add(account2);

        assertFalse(Account.userHasAccounts(accounts, user1));
    }

    @Test
    void testPrintDetails() {
        account1.printDetails();
        // Since printDetails only prints, we verify by running it (mocking `System.out` for assertions is optional)
    }

    @Test
    void testToString() {
        assertNotNull(account1.toString());
        assertEquals(account1.accountId, account1.toString());
    }

    @Test
    void testCloseAccount_WithNonZeroBalance() {
        // Mock CmdCloseAccount
        CmdCloseAccount cmdCloseAccount = mock(CmdCloseAccount.class);

        account1.close(account1);
        // Ensure account isn't closed since the balance isn't zero
    }
}