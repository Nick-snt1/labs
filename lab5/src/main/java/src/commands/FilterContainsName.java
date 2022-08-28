package src.commands;

import src.elements.*;
import src.core.*;

/** Class, makes object from command */
public class FilterContainsName implements Command {

    /** Handler to execute command */
    private CollectionHandler collectionHandler;

    /** String needs to execute command */
    private String name;

    /**
        Creates new object
        @param collectionHandler handler to execute command
        @param name object, which need to execute command
    */
    public FilterContainsName(CollectionHandler collectionHandler, String name) {
        this.collectionHandler = collectionHandler;
        this.name = name;
    }

    @Override
    public void execute() {
        collectionHandler.filterContainsName(name);
    }
}
