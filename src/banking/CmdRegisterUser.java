package banking;

public class CmdRegisterUser extends RecordedCommand {
    private User user;

    @Override
    public void execute(String[] cmdParts) {
        // cmd[0] is username, cmd[1] is password
        user = new User(cmdParts[0], cmdParts[1],
                cmdParts[2].equals("client") ? new Client(cmdParts[0]) : new Employee(cmdParts[0]));
        UserDatabase.getInstance().addUser(user);
        System.out.println("User added successfully!");
        addUndoCommand(this);
        clearRedoList();
    }

    @Override
    public void undoMe() {
        UserDatabase.getInstance().removeUser(user);
        System.out.println("Undo successful " + user + " removed.");
        super.addRedoCommand(this);
    }

    @Override
    public void redoMe() {
        UserDatabase.getInstance().addUser(user);
        System.out.println("Redo successful. " + user + " readded");
        super.addUndoCommand(this);
    }

}
