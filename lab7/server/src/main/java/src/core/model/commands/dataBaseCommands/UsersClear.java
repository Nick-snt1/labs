package src.core.model.commands.dataBaseCommands;

import src.core.model.commands.Command;
import src.core.model.handlers.UsersCollectionHandler;
import src.util.User;

public class UsersClear implements Command<User> {

    private final UsersCollectionHandler handler;

    private final User user;

    public UsersClear(UsersCollectionHandler handler, User user){
        this.handler = handler;
        this.user = user;
    }

    @Override
    public User execute() throws Exception {
        return handler.usersClear(user);
    }
}
