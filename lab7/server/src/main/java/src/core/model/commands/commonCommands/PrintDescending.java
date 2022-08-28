package src.core.model.commands.commonCommands;

import src.core.model.commands.Command;
import src.core.model.elements.*;
import src.core.model.handlers.CollectionHandler;

/** Class, makes object from command */
public class PrintDescending implements Command<String> {

    /** Handler to execute command */
    private CollectionHandler collectionHandler;

    /**
        Creates new object
        @param collectionHandler handler to execute command
    */
    public PrintDescending(CollectionHandler collectionHandler) {
        this.collectionHandler = collectionHandler;
    }

    @Override
    public String execute() {
        return collectionHandler.printDescending();
    }
}
