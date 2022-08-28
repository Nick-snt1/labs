package src.core.model.commands;

import src.core.model.elements.*;
import src.core.model.handlers.*;

/** Class, makes object from command */
public class ExecuteScript implements Command {

    /** Handler to execute command */
    private CollectionHandler handler;

    /** String needs to execute command */
    private String fileName;

    /** Invokers objects needs to execute command */
    private CommandHandler invoker;

    /**
        Creates new object
        @param handler to execute command
        @param fileName object, which need to execute command
        @param invoker object needs to execute command
    */
    public ExecuteScript(CollectionHandler handler, String fileName, CommandHandler invoker) {
        this.fileName = fileName;
        this.invoker = invoker;
        this.handler = handler;
    }

    @Override
    public String execute() {
        return handler.help();
    }
}
