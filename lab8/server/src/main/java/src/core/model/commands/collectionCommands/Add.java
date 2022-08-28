package src.core.model.commands.collectionCommands;

import src.core.model.commands.Command;
import src.core.model.elements.*;
import src.core.model.handlers.CollectionHandler;
import src.core.model.commands.dataBaseCommands.UsersAdd;
import src.util.Respond;

public class Add implements Command<Respond> {

    private CollectionHandler collectionHandler;

    private UsersAdd usersAdd;

    public Add(CollectionHandler collectionHandler, UsersAdd usersAdd) {
        this.collectionHandler = collectionHandler;
        this.usersAdd = usersAdd;
    }

    @Override
    public Respond execute() throws Exception {
        HumanBeing human = usersAdd.execute();
        return human == null ?
            new Respond("Something went wrong, element wasnt added") :
                collectionHandler.add(human);
    }
}
