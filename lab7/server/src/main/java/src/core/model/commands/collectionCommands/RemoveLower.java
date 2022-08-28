package src.core.model.commands.collectionCommands;

import src.core.model.commands.Command;
import src.core.model.elements.*;
import src.core.model.handlers.CollectionHandler;
import src.core.model.commands.dataBaseCommands.UsersRemoveLower;

public class RemoveLower implements Command<String> {

    private CollectionHandler handler;

    private UsersRemoveLower usersRemoveLower;

    public RemoveLower(CollectionHandler handler, UsersRemoveLower usersRemoveLower) {
        this.handler = handler;
        this.usersRemoveLower = usersRemoveLower;
    }

    @Override
    public String execute() throws Exception {
        HumanBeing h = usersRemoveLower.execute();
        return h == null ? "You don't have elemets lower that the given" : handler.removeLower(h);
    }
}
