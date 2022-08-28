package src.core.commands;

import src.core.handlers.*;
import src.util.User;

public class ExecuteScript implements Command<String> {

    private ProgrammHandler handler;

    private String fileName;

    private User user;

    private CommandHandler commandHandler;

    public ExecuteScript(ProgrammHandler handler, String fileName, User user, CommandHandler commandHandler) {
        this.fileName = fileName;
        this.commandHandler = commandHandler;
        this.handler = handler;
        this.user = user;
    }

    @Override
    public String execute() {
        return handler.executeScript(fileName, user, commandHandler);
    }
}
