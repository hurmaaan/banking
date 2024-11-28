package banking;

public class CmdRemoveUser extends RecordedCommand {
    private User user;

    @Override
    public void execute(String[] cmdParts) {
        // cmd[0] is username
        user = UserDatabase.getInstance().findUser(cmdParts[0]);
        UserDatabase.getInstance().removeUser(user);
        System.out.println("Employee removed successfully");
        addUndoCommand(this);
        clearRedoList();
    }

    @Override
    public void undoMe() {
        UserDatabase.getInstance().addUser(user);
        System.out.println("Undo Successful. ReAdded Employee: " + user);
        super.addRedoCommand(this);
    }

    @Override
    public void redoMe() {
        UserDatabase.getInstance().removeUser(user);
        System.out.println("Redo Successful. Removed Employee: " + user);
        super.addUndoCommand(this);
    }

}
