package src.commands;

import src.elements.*;
import src.core.*;

/** Class, makes object from command */
public class RemoveById implements Command {

    /** Handler to execute command */
    private CollectionHandler collectionHandler;

    /** Value, to execute command */
    private long id;

    /**
        Creates new object
        @param collectionHandler handler to execute command
        @param id value, which need to execute command
    */
    public RemoveById(CollectionHandler collectionHandler, long id) {
        this.collectionHandler = collectionHandler;
        this.id = id;
    }

    @Override
    public void execute() {
        collectionHandler.removeById(id);
    }
}
