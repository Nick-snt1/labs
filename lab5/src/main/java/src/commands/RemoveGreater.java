package src.commands;

import src.elements.*;
import src.core.*;

/** Class, makes object from command */
public class RemoveGreater implements Command {

    /** Handler to execute command */
    private CollectionHandler collectionHandler;

    /** HumanBeing objects, which need to execute command */
    private HumanBeing human;

    /**
        Creates new object
        @param collectionHandler handler to execute command
        @param human object, which need to execute command
    */
    public RemoveGreater(CollectionHandler collectionHandler, HumanBeing human) {
        this.collectionHandler = collectionHandler;
        this.human = human;
    }

    @Override
    public void execute() {
        collectionHandler.removeGreater(human);
    }
}
