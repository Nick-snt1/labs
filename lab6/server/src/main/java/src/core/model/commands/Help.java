package src.core.model.commands;

import src.core.model.elements.*;
import src.core.model.handlers.CollectionHandler;

/** Class, makes object from command */
public class Help implements Command {

    /** Handler to execute command */
    private CollectionHandler collectionHandler;

    /**
        Creates new object
        @param collectionHandler handler to execute command
    */
    public Help(CollectionHandler collectionHandler) {
        this.collectionHandler = collectionHandler;
    }

    @Override
    public String execute() {
        return collectionHandler.help();
    }
}
