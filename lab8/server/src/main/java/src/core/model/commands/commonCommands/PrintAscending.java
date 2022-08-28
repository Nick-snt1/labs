package src.core.model.commands.commonCommands;

import src.core.model.commands.Command;
import src.core.model.elements.*;
import src.core.model.handlers.CollectionHandler;
import src.util.Respond;

/** Class, makes object from command */
public class PrintAscending implements Command<Respond> {

    /** Handler to execute command */
    private CollectionHandler collectionHandler;

    /**
        Creates new object
        @param collectionHandler handler to execute command
    */
    public PrintAscending(CollectionHandler collectionHandler) {
        this.collectionHandler = collectionHandler;
    }

    @Override
    public Respond execute() {
        return collectionHandler.printAscending();
    }
}
