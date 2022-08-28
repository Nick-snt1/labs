package src.core.model.commands.collectionCommands;

import src.core.model.commands.Command;
import src.core.model.elements.*;
import src.core.model.handlers.CollectionHandler;
import src.core.model.commands.dataBaseCommands.UsersAddIfMin;
import src.util.Respond;

public class AddIfMin implements Command<Respond> {

    private CollectionHandler collectionHandler;

    private UsersAddIfMin usersAddIfMin;

    public AddIfMin(CollectionHandler collectionHandler, UsersAddIfMin usersAddIfMin) {
        this.collectionHandler = collectionHandler;
        this.usersAddIfMin = usersAddIfMin;
    }

    @Override
    public Respond execute() throws Exception {
        HumanBeing human = usersAddIfMin.execute();
        return human == null
            ? new Respond("Given element is bigger than the least at the collection")
                : collectionHandler.addIfMin(human);
    }
}
