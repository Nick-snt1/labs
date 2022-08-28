package src.commands;

import src.elements.*;
import src.core.*;

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
    public void execute() {
        handler.save();
    }
}
