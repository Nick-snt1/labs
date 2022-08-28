package src.commands;

import src.elements.*;
import src.core.*;

/** Class, makes object from command */
public class Update implements Command {

    /** Handler to execute command */
    private CollectionHandler collectionHandler;

    /** Value, to execute command */
    private long id;

    /** HumanBeing objects, which need to execute command */
    private HumanBeing human;

    /**
        Creates new object
        @param collectionHandler handler to execute command
        @param id value, which need to execute command
        @param human object, which need to execute command
    */
    public Update(CollectionHandler collectionHandler, long id, HumanBeing human) {
        this.collectionHandler = collectionHandler;
        this.id = id;
        this.human = human;
    }

    @Override
    public void execute() {
        collectionHandler.updateById(id, human);
    }
}
