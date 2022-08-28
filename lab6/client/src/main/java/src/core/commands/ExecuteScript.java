package src.core.commands;

import src.core.handlers.*;

public class ExecuteScript implements Command {

    private ProgrammHandler handler;

    private String fileName;

    private CommandHandler commandHandler;

    public ExecuteScript(ProgrammHandler handler, String fileName, CommandHandler commandHandler) {
        this.fileName = fileName;
        this.commandHandler = commandHandler;
        this.handler = handler;
    }

    @Override
    public void execute() {
        handler.executeScript(fileName, commandHandler);
    }
}
