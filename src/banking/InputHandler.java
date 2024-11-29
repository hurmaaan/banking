package banking;

import java.util.Scanner;

public class InputHandler {

    private static InputHandler instance;
    private final Scanner scanner;

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

    public double getNextDouble() {
        return scanner.nextDouble();
    }

    public int getNextInt() {
        return scanner.nextInt();
    }

    public void close() {
        if (instance != null) {
            scanner.close();
        }
    }

}
