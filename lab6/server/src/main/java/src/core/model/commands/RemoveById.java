package src.core.model.commands;

import src.core.model.elements.*;
import src.core.model.handlers.CollectionHandler;

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
    public String execute() {
        return collectionHandler.removeById(id);
    }
}
