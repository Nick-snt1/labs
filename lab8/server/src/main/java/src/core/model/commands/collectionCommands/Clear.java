package src.core.model.commands.collectionCommands;

import src.core.model.commands.Command;
import src.core.model.elements.*;
import src.core.model.handlers.CollectionHandler;
import src.core.model.commands.dataBaseCommands.UsersClear;
import src.util.User;
import src.util.Respond;

public class Clear implements Command<Respond> {

    private CollectionHandler handler;

    private UsersClear usersClear;

    public Clear(CollectionHandler handler, UsersClear usersClear) {
        this.handler = handler;
        this.usersClear = usersClear;
    }

    @Override
    public Respond execute() throws Exception {
        User user = usersClear.execute();
        return user == null ? new Respond("Your collection is clear") : handler.clear(user);
    }
}
