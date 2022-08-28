package src.core.model.commands.collectionCommands;

import src.core.model.commands.Command;
import src.core.model.elements.*;
import src.core.model.handlers.CollectionHandler;
import src.core.model.commands.dataBaseCommands.UsersRemoveGreater;

public class RemoveGreater implements Command<String> {

    private CollectionHandler handler;

    private UsersRemoveGreater usersRemoveGreater;

    public RemoveGreater(CollectionHandler handler, UsersRemoveGreater usersRemoveGreater) {
        this.handler = handler;
        this.usersRemoveGreater = usersRemoveGreater;
    }

    @Override
    public String execute() throws Exception {
        HumanBeing h = usersRemoveGreater.execute();
        return h == null ? "You don't have elemets bigger that the given" : handler.removeGreater(h);
    }
}
