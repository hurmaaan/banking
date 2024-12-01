package banking;

import java.io.InputStream;
import java.util.Scanner;

public class InputHandler {

    private static InputHandler instance;
    private Scanner scanner;

    private InputHandler() {
        scanner = new Scanner(System.in);
    }


    public void setScanner(InputStream in) {
        scanner.close();
        scanner = new Scanner(in);
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

        scanner.close();
        instance = null;

    }

}
