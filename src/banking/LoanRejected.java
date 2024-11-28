package banking;

public class LoanRejected implements LoanStatus {

    private static LoanRejected instance = null;

    private LoanRejected() {

    }

    public static LoanRejected getInstance() {
        if (instance == null) {
            instance = new LoanRejected();
        }
        return instance;
    }

    @Override
    public boolean canRepay() {
        System.out.println("Loan Rejected. Cannot Proceed.");

        return false;
    }

}
