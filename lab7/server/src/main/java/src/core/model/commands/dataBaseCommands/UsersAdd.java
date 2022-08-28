package src.core.model.commands.dataBaseCommands;

import src.core.model.commands.Command;
import src.core.model.handlers.UsersCollectionHandler;
import src.core.model.elements.HumanBeing;

public class UsersAdd implements Command<HumanBeing> {

    private final UsersCollectionHandler handler;

    private final HumanBeing human;

    public UsersAdd(UsersCollectionHandler handler, HumanBeing human) {
        this.handler = handler;
        this.human = human;
    }

    @Override
    public HumanBeing execute() throws Exception {
        return handler.usersAdd(human);
    }
}
