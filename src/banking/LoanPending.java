package banking;

public class LoanPending implements LoanStatus {

    private static LoanPending instance = null;

    private LoanPending() {

    }

    public static LoanPending getInstance() {
        if (instance == null) {
            instance = new LoanPending();
        }
        return instance;
    }

    @Override
    public boolean canRepay() {
        return false;
    }

}
