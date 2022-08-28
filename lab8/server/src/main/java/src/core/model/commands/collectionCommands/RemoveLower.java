package src.core.model.commands.collectionCommands;

import src.core.model.commands.Command;
import src.core.model.elements.*;
import src.core.model.handlers.CollectionHandler;
import src.core.model.commands.dataBaseCommands.UsersRemoveLower;
import src.util.Respond;

public class RemoveLower implements Command<Respond> {

    private CollectionHandler handler;

    private UsersRemoveLower usersRemoveLower;

    public RemoveLower(CollectionHandler handler, UsersRemoveLower usersRemoveLower) {
        this.handler = handler;
        this.usersRemoveLower = usersRemoveLower;
    }

    @Override
    public Respond execute() throws Exception {
        HumanBeing h = usersRemoveLower.execute();
        return h == null ? new Respond("You don't have elemets lower that the given") : handler.removeLower(h);
    }
}
