package src.core.model.commands.dataBaseCommands;

import src.core.model.commands.Command;
import src.core.model.handlers.UsersCollectionHandler;
import src.util.User;

public class UsersRemoveById implements Command<User> {

    private final UsersCollectionHandler handler;

    private final User user;

    private final long id;

    public UsersRemoveById(UsersCollectionHandler handler, User user, long id){
        this.handler = handler;
        this.user = user;
        this.id = id;
    }

    @Override
    public User execute() throws Exception {
        return handler.usersRemoveById(user, id);
    }
}
