package src.core.model.commands.collectionCommands;

import src.core.model.commands.Command;
import src.core.model.elements.*;
import src.core.model.handlers.CollectionHandler;
import src.core.model.commands.dataBaseCommands.UsersUpdate;
import src.util.Respond;

public class Update implements Command<Respond> {

    private CollectionHandler handler;

    private long id;

    private UsersUpdate usersUpdate;

    public Update(CollectionHandler handler, long id, UsersUpdate usersUpdate) {
        this.handler = handler;
        this.id = id;
        this.usersUpdate = usersUpdate;
    }

    @Override
    public Respond execute() throws Exception {
        HumanBeing human = usersUpdate.execute();
        return human == null ? new Respond("You don't have element with id " + id) : handler.updateById(id, human);
    }
}
