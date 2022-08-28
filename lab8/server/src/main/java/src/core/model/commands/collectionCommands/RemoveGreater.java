package src.core.model.commands.collectionCommands;

import src.core.model.commands.Command;
import src.core.model.elements.*;
import src.core.model.handlers.CollectionHandler;
import src.core.model.commands.dataBaseCommands.UsersRemoveGreater;
import src.util.Respond;

public class RemoveGreater implements Command<Respond> {

    private CollectionHandler handler;

    private UsersRemoveGreater usersRemoveGreater;

    public RemoveGreater(CollectionHandler handler, UsersRemoveGreater usersRemoveGreater) {
        this.handler = handler;
        this.usersRemoveGreater = usersRemoveGreater;
    }

    @Override
    public Respond execute() throws Exception {
        HumanBeing h = usersRemoveGreater.execute();
        return h == null ? new Respond("You don't have elemets bigger that the given") : handler.removeGreater(h);
    }
}
