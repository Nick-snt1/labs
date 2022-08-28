package src.core.commands;

import src.core.handlers.*;
import src.util.*;

public class ExecuteScript implements Command<Respond> {

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
    public Respond execute() {
        return handler.executeScript(fileName, user, commandHandler);
    }
}
