package src.core.model.commands.collectionCommands;

import src.core.model.commands.Command;
import src.core.model.elements.*;
import src.core.model.handlers.CollectionHandler;
import src.core.model.commands.dataBaseCommands.UsersRemoveById;
import src.util.User;
import src.util.Respond;

public class RemoveById implements Command<Respond> {

    private CollectionHandler handler;

    private long id;

    private UsersRemoveById usersRemoveById;

    public RemoveById(CollectionHandler handler, long id, UsersRemoveById usersRemoveById) {
        this.handler = handler;
        this.usersRemoveById = usersRemoveById;
        this.id = id;
    }

    @Override
    public Respond execute() throws Exception {
        User user = usersRemoveById.execute();
        return user == null ? new Respond("You dont have element with id " + id) : handler.removeById(id, user);
    }
}
