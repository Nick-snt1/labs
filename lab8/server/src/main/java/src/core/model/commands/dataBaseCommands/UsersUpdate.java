package src.core.model.commands.dataBaseCommands;

import src.core.model.commands.Command;
import src.core.model.handlers.UsersCollectionHandler;
import src.core.model.elements.HumanBeing;

public class UsersUpdate implements Command<HumanBeing> {

    private final UsersCollectionHandler handler;

    private final HumanBeing human;

    private final long id;

    public UsersUpdate(UsersCollectionHandler handler, HumanBeing human, long id){
        this.handler = handler;
        this.human = human;
        this.id = id;
    }

    @Override
    public HumanBeing execute() throws Exception {
        return handler.usersUpdate(id ,human);
    }
}
