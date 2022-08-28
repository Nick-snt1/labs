package src.core.model.commands.dataBaseCommands;

import src.core.model.commands.Command;
import src.core.model.handlers.UsersCollectionHandler;
import src.util.User;

public class Registration implements Command<String> {

    private final UsersCollectionHandler handler;

    private final User user;

    public Registration(UsersCollectionHandler handler, User user){
        this.handler = handler;
        this.user = user;
    }

    @Override
    public String execute() throws Exception {
        return handler.registration(user);
    }
}
