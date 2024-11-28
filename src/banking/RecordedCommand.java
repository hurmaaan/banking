package banking;

import java.util.ArrayList;

public abstract class RecordedCommand implements Command {
    private static ArrayList<RecordedCommand> undoList = new ArrayList<>();
    private static ArrayList<RecordedCommand> redoList = new ArrayList<>();

    public static void reset() {
        undoList.clear();
        redoList.clear();
    }

    public abstract void undoMe();

    public abstract void redoMe();

    protected static void addUndoCommand(RecordedCommand cmd) {
        undoList.add(cmd);
    }

    protected static void addRedoCommand(RecordedCommand cmd) {
        redoList.add(cmd);
    }

    /* clear redo list */
    protected static void clearRedoList() {
        redoList.clear();
    }

    /* carry out undo/redo */
    public static void undoOneCommand() {

        if (undoList.isEmpty()) {
            System.out.println("Nothing to undo.");
        } else {
            RecordedCommand rc = undoList.remove(undoList.size() - 1);
            rc.undoMe();
        }

    }

    public static void redoOneCommand() {
        if (redoList.isEmpty()) {
            System.out.println("Nothing to redo.");
        } else {
            redoList.remove(redoList.size() - 1).redoMe();
        }

    }
}
