package banking;

public interface Command {
    void execute(String[] cmdParts);
}
