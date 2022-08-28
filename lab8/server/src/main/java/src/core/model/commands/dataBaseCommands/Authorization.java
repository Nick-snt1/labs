package src.core.model.commands.dataBaseCommands;

import src.core.model.commands.Command;
import src.core.model.handlers.UsersCollectionHandler;
import src.util.User;
import src.util.Respond;

public class Authorization implements Command<Respond> {

    private final UsersCollectionHandler handler;

    private final User user;

    public Authorization(UsersCollectionHandler handler, User user){
        this.handler = handler;
        this.user = user;
    }

    @Override
    public Respond execute() throws Exception {
        return handler.authorization(user);
    }
}
