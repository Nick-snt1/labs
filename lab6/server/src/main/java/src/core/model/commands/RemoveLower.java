package src.core.model.commands;

import src.core.model.elements.*;
import src.core.model.handlers.CollectionHandler;

/** Class, makes object from command */
public class RemoveLower implements Command {

    /** Handler to execute command */
    private CollectionHandler collectionHandler;

    /** HumanBeing objects, which need to execute command */
    private HumanBeing human;

    /**
        Creates new object
        @param collectionHandler handler to execute command
        @param human object, which need to execute command
    */
    public RemoveLower(CollectionHandler collectionHandler, HumanBeing human) {
        this.collectionHandler = collectionHandler;
        this.human = human;
    }

    @Override
    public String execute() {
        return collectionHandler.removeLower(human);
    }
}
