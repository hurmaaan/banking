package banking;

public class LoanApproved implements LoanStatus {

    private static LoanApproved instance = null;

    private LoanApproved() {

    }

    public static LoanApproved getInstance() {
        if (instance == null) {
            instance = new LoanApproved();
        }
        return instance;
    }

    @Override
    public boolean canRepay() {
        return true;
    }

}
