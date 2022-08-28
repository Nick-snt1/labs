package src.core.model.commands;

import src.core.model.elements.*;
import src.core.model.handlers.CollectionHandler;

/** Class, makes object from command */
public class Save implements Command{

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
    public String execute() {
        return handler.save();
    }
}
