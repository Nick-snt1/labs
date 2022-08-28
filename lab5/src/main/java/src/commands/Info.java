package src.commands;

import src.elements.*;
import src.core.*;

/** Class, makes object from command */
public class Info implements Command {

    /** Handler to execute command */
    private CollectionHandler collectionHandler;

    /**
        Creates new object
        @param collectionHandler handler to execute command
    */
    public Info(CollectionHandler collectionHandler) {
        this.collectionHandler = collectionHandler;
    }

    @Override
    public void execute() {
        collectionHandler.info();
    }
}
