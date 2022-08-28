package src.core.model.commands.commonCommands;

import src.core.model.commands.Command;
import src.core.model.elements.*;
import src.core.model.handlers.CollectionHandler;
import src.util.Respond;

/** Class, makes object from command */
public class Show implements Command<Respond> {

    /** Handler to execute command */
    private CollectionHandler collectionHandler;

    public Show(CollectionHandler collectionHandler) {
        this.collectionHandler = collectionHandler;
    }

    @Override
    public Respond execute() {
        return collectionHandler.show();
    }
}
