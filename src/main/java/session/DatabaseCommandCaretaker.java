package session;

import com.j256.ormlite.misc.TransactionManager;

import java.util.Stack;
import java.util.concurrent.Callable;

// Use DI to insert single instance of this into each service
public class DatabaseCommandCaretaker {


    // Variables
    private Stack<Callable<Boolean>> commandUndoHistory;
    private Stack<Callable<Boolean>> commandRedoHistory;


    // Constructors
    public DatabaseCommandCaretaker(TransactionManager transactionManager) {
        this.commandUndoHistory = new Stack<>();
        this.commandRedoHistory = new Stack<>();
    }


    // Getters and Setters


    // Initialisation methods


    // Other methods
    public void recordCommand(Callable<Boolean> callableCommand) {
        commandUndoHistory.push(callableCommand);
        if (!commandRedoHistory.isEmpty()) {
            commandRedoHistory.clear();     // Empties the redo list, as updating past a certain point destroys the ability to "redo"
        }
    }


    public Callable<Boolean> undo() {
        if (!commandUndoHistory.isEmpty()) {
            Callable<Boolean> undoCallable = commandUndoHistory.pop();
            commandRedoHistory.push(undoCallable);
            return undoCallable;
        } else {
            // TODO Insert logging indicating that undo queue is empty
            return null;
        }
    }

    public Callable<Boolean> redo() {
        if (!commandRedoHistory.isEmpty()) {
            Callable<Boolean> redoCallable = commandRedoHistory.pop();
            commandUndoHistory.push(redoCallable);
            return redoCallable;
        } else {
            // TODO Insert logging indicating that undo queue is empty
            return null;
        }
    }


}
