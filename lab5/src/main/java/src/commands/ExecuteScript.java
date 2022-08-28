package src.commands;

import src.elements.*;
import src.core.*;

/** Class, makes object from command */
public class ExecuteScript implements Command {

    /** Handler to execute command */
    private CollectionHandler handler;

    /** String needs to execute command */
    private String fileName;

    /** Invokers objects needs to execute command */
    private Invoker invoker;

    /**
        Creates new object
        @param handler to execute command
        @param fileName object, which need to execute command
        @param invoker object needs to execute command
    */
    public ExecuteScript(CollectionHandler handler, String fileName, Invoker invoker) {
        this.fileName = fileName;
        this.invoker = invoker;
        this.handler = handler;
    }

    @Override
    public void execute() {
        handler.executeScript(fileName, invoker);
    }
}
