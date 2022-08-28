package src.core.model.commands.collectionCommands;

import src.core.model.commands.Command;
import src.core.model.elements.*;
import src.core.model.handlers.CollectionHandler;
import src.core.model.commands.dataBaseCommands.UsersAdd;

public class Add implements Command<String> {

    private CollectionHandler collectionHandler;

    private UsersAdd usersAdd;

    public Add(CollectionHandler collectionHandler, UsersAdd usersAdd) {
        this.collectionHandler = collectionHandler;
        this.usersAdd = usersAdd;
    }

    @Override
    public String execute() throws Exception {
        HumanBeing human = usersAdd.execute();
        return human == null ? "Something went wrong, element wasnt added" : collectionHandler.add(human);
    }
}
