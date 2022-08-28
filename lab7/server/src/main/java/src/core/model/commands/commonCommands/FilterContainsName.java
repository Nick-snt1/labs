package src.core.model.commands.commonCommands;

import src.core.model.commands.Command;
import src.core.model.elements.*;
import src.core.model.handlers.CollectionHandler;

/** Class, makes object from command */
public class FilterContainsName implements Command<String> {

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
    public String execute() {
        return collectionHandler.filterContainsName(name);
    }
}
