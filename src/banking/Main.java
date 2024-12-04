package banking;


import banking.Bank;
import banking.InputHandler;


public class Main {
    public static void main(String[] args) {

        InputHandler inputHandler = InputHandler.getInstance();
        Bank bank = Bank.getInstance();

        bank.start();

        inputHandler.close();

    }

}