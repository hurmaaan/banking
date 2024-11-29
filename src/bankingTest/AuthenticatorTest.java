package bankingTest;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import banking.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.InputStream;
import java.io.ByteArrayInputStream;

public class AuthenticatorTest {

	private PrintStream originalOut;
    private ByteArrayOutputStream outputStream;
    private InputStream originalIn;
    
    private User user;
    private Client client;
    private UserDatabase userDatabase;
    private Authenticator authenticator;

    @BeforeEach
    private void setUp() {
    	InputHandler.resetInstance();
    	
    	// Redirect System.out to capture output
        originalOut = System.out;
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // Redirect System.in to simulate user input
        originalIn = System.in;
    }

    @Test
    void testAuthenticateSuccess() throws Exception {
    	 String simulatedInput = "user1\npwd1\n";
         System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
         
        client = new Client("user1");
        user = new User("user1", "pwd1", client);
        userDatabase = UserDatabase.getInstance();
        userDatabase.addUser(user);
        authenticator = new Authenticator(userDatabase);

        // Redirect output to capture printed messages
        User authenticatedUser = authenticator.authenticate();

        // Verify that the authentication was successful
        assertNotNull(authenticatedUser);
        assertEquals(user.toString(), authenticatedUser.toString()); 

        // Verify the output (Login successful message)
        String output = outputStream.toString();
        assertTrue(output.contains("Login successful! Welcome, Client."));
    }

    
    @Test
    void testAuthenticateInvalidPassword() {
        String simulatedInput = "user1\nwrongPassword\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        // Set up valid user
        client = new Client("user1");
        user = new User("user1", "pwd1", client);
        userDatabase = UserDatabase.getInstance();
        userDatabase.addUser(user);
        authenticator = new Authenticator(userDatabase);

        // Perform authentication
        User authenticatedUser = authenticator.authenticate();

        // Verify that authentication failed
        assertNull(authenticatedUser);

        // Verify the output
        String output = outputStream.toString();
        assertTrue(output.contains("Login failed! Invalid password."));
    }

    @Test
    void testAuthenticateUserDoesNotExist() {
    	String simulatedInput = "invalidUser\nany\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        // Set up valid user
        client = new Client("user1");
        user = new User("user1", "pwd1", client);
        userDatabase = UserDatabase.getInstance();
        userDatabase.addUser(user);
        authenticator = new Authenticator(userDatabase);

        // Perform authentication
        User authenticatedUser = authenticator.authenticate();

        // Verify that authentication failed
        assertNull(authenticatedUser);

        // Verify the output
        String output = outputStream.toString();
        assertTrue(output.contains("Login failed! User does not exist."));
    }

}
