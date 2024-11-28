package bankingTest;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import banking.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.InputStream;
import java.io.ByteArrayInputStream;

public class AuthenticatorTest {

    private PrintStream oldPrintStream;
    private ByteArrayOutputStream bos;
    
    private User user1;
    private User user2;

    // Setup output redirection
    private void setOutput() {
        oldPrintStream = System.out;
        bos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(bos));
    }

    private String getOutput() {
        System.setOut(oldPrintStream);
        return bos.toString();
    }
    
//    @BeforeEach
    void setUp() throws Exception {
    	setOutput();
    	Client client1 = new Client("user1");
    	Client client2 = new Client("user2");
        user1 = new User("user1", "pwd1", client1);
        user2 = new User("user2", "pwd2", client2);
//        account1 = new Account(1000.0, savings, user1); // id = 109
//        account2 = new Account(2000.0, checking, user2); // id = 110
    }
    
    @Test
    void test1() {
    	assertEquals(1,1);
    }

//    @Test
//    void testAuthenticateSuccess() throws Exception {
//        // Set up a user database with a valid user
//    	User user = new User("test_customer", "testcustomer123", new Client("test_customer"));
//    	UserDatabase userDatabase = UserDatabase.getInstance();
//        userDatabase.addUser(user);
//        
//        User found_user = userDatabase.findUser("test_customer");
//        System.out.println("User found: " + user);  // Debug line
//
//
//        String simulatedInput = "test_customer\ntestcustomer123\n";
//        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
//
//        // Redirect output to capture printed messages
//        setOutput();
//
//        // Create the Authenticator and authenticate
//        Authenticator authenticator = new Authenticator(userDatabase);
//        User authenticatedUser = authenticator.authenticate();
//        System.out.println("HERE PEE");  // Debug line
//
//        // Verify that the authentication was successful
//        assertNotNull(authenticatedUser);
//        assertEquals(user1, authenticatedUser); 
//
//        // Verify the output (Login successful message)
//        String output = getOutput();
//        assertTrue(output.contains("Login successful! Welcome, Admin."));
//    }
//
//    @Test
//    void testAuthenticateInvalidPassword() {
//        // Set up a user database with a valid user
//        User testUser = new User("testUser", "password123", "Admin");
//        UserDatabase userDatabase = new UserDatabase();
//        userDatabase.addUser(testUser);
//
//        // Set the input stream to simulate incorrect password
//        String simulatedInput = "testUser\nwrongPassword\n";
//        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
//
//        // Redirect output to capture printed messages
//        setOutput();
//
//        // Create the Authenticator and authenticate
//        Authenticator authenticator = new Authenticator(userDatabase);
//        User authenticatedUser = authenticator.authenticate();
//
//        // Verify that the authentication failed
//        assertNull(authenticatedUser);
//
//        // Verify the output (User does not exist message)
//        String output = getOutput();
//        assertTrue(output.contains("User does not exist"));
//    }
//
    @Test
    void testAuthenticateUserDoesNotExist() {
        // Set up an empty user database
        UserDatabase userDatabase = UserDatabase.getInstance();

        // Set the input stream to simulate a non-existent username
        String simulatedInput = "nonExistentUser\nanyPassword\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        // Redirect output to capture printed messages
        setOutput();

        // Create the Authenticator and authenticate
        Authenticator authenticator = new Authenticator(userDatabase);
        User authenticatedUser = authenticator.authenticate();

        // Verify that the authentication failed
        assertNull(authenticatedUser);

        // Verify the output (Login failed message)
        String output = getOutput();
        assertTrue(output.contains("Login failed! Invalid password."));
    }

    // Reset System.in to its original state after each test
    @AfterEach
    void restoreSystemInStream() {
        System.setIn(System.in);
    }

    @AfterEach
    void restoreSystemOutStream() {
        System.setOut(oldPrintStream);
    }
}
