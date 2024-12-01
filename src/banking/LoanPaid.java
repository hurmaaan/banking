package banking;

public class LoanPaid implements LoanStatus {

    private static LoanPaid instance = null;

    private LoanPaid() {

    }

    public static LoanPaid getInstance() {
        if (instance == null) {
            instance = new LoanPaid();
        }
        return instance;
    }

    @Override
    public boolean canRepay() {
        System.out.println("Loan already repaid!");
        return false;
    }

}
