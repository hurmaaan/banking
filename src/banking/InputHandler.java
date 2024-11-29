package banking;

import java.util.Scanner;

public class InputHandler {

    private static InputHandler instance;
    private Scanner scanner;

    private InputHandler() {
        scanner = new Scanner(System.in);
    }

    public static InputHandler getInstance() {
        if (instance == null) {
            instance = new InputHandler();
        }
        return instance;
    }

    public String getNextLine() {
        return scanner.nextLine();
    }

    public int getNextInt() {
        return scanner.nextInt();
    }

    public double getNextDouble() {
        return scanner.nextDouble();
    }

    public void close() {
        if (scanner != null) {
            scanner.close();
        }
    }

    // Add a method to set a custom Scanner (for testing purposes)
    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    // Add a method to reset the singleton (for testing purposes)
    public static void resetInstance() {
        instance = null;
    }
}