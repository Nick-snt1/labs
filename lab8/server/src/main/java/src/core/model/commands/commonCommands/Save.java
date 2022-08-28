package src.core.model.commands.commonCommands;

import src.core.model.commands.Command;
import src.core.model.elements.*;
import src.core.model.handlers.CollectionHandler;
import src.util.Respond;

/** Class, makes object from command */
public class Save implements Command<Respond> {

    /** Handler to execute command */
    CollectionHandler handler;

    /**
        Creates new object
        @param handler to execute command
    */
    public Save(CollectionHandler handler) {
        this.handler = handler;
    }

    @Override
    public Respond execute() {
        return handler.save();
    }
}
