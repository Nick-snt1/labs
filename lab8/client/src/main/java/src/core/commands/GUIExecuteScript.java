package src.core.commands;

import src.core.handlers.*;
import src.util.*;

public class GUIExecuteScript implements Command<Respond> {

    private ProgrammHandler handler;

    private String fileName;

    private User user;

    private CommandHandler commandHandler;

    public GUIExecuteScript(ProgrammHandler handler, String fileName, User user, CommandHandler commandHandler) {
        this.fileName = fileName;
        this.commandHandler = commandHandler;
        this.handler = handler;
        this.user = user;
    }

    @Override
    public Respond execute() {
        return handler.guiExecuteScript(fileName, user, commandHandler);
    }
}
