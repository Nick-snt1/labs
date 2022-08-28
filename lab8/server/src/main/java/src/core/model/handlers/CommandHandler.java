package src.core.model.handlers;

import src.core.model.commands.Command;
import src.core.model.commands.collectionCommands.*;
import src.core.model.commands.guiCollectionCommands.*;
import src.core.model.commands.commonCommands.*;
import src.core.model.commands.dataBaseCommands.*;
import src.core.model.elements.*;

import src.util.User;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import java.util.function.BiFunction;
import java.util.function.Function;
//import static java.util.Map.entry;
import src.util.Respond;

public class CommandHandler {

    private CollectionHandler handler;

    private UsersCollectionHandler usersHandler;

    private List<Command> commands = new ArrayList<>();

    private Map<String, Supplier<Command<Respond>>> noArgMap    = new HashMap<>();
    private Map<String, Supplier<Command<Respond>>> guiNoArgMap = new HashMap<>();

    private Map<String, Function<HumanBeing, Command<Respond>>> humanMap = new HashMap<>();
    private Map<String, Function<String,     Command<Respond>>> strMap   = new HashMap<>();
    private Map<String, Function<User,       Command<Respond>>> userMap  = new HashMap<>();

    private Map<String, BiFunction<String, User,       Command<Respond>>> strUserMap  = new HashMap<>();
    private Map<String, BiFunction<String, HumanBeing, Command<Respond>>> strHumanMap = new HashMap<>();

    {
        noArgMap.put("print_descending", () -> new PrintDescending(handler));
        noArgMap.put("print_ascending",  () -> new PrintAscending(handler));
        noArgMap.put("save",             () -> new Save(handler));
        noArgMap.put("help",             () -> new Help(handler));
        noArgMap.put("show",             () -> new Show(handler));

        guiNoArgMap.put("guiShow",  () -> new GUIShow(handler));

        humanMap.put("add",            x -> new Add(handler,           new UsersAdd(usersHandler, x)));
        humanMap.put("add_if_min",     x -> new AddIfMin(handler,      new UsersAddIfMin(usersHandler, x)));
        humanMap.put("remove_greater", x -> new RemoveGreater(handler, new UsersRemoveGreater(usersHandler, x)));
        humanMap.put("remove_lower",   x -> new RemoveLower(handler,   new UsersRemoveLower(usersHandler, x)));

        strMap.put("filter_contains_name", x -> new FilterContainsName(handler, x));

        userMap.put("authorization", x -> new Authorization(usersHandler, x));
        userMap.put("registration",  x -> new Registration(usersHandler, x));
        userMap.put("info",          x -> new Info(handler, x));
        userMap.put("clear",         x -> new Clear(handler, new UsersClear(usersHandler, x)));

        strUserMap.put("remove_by_id",  (id, user) -> new RemoveById(
            handler, Long.parseLong(id), new UsersRemoveById(usersHandler, user, Long.parseLong(id))));

        strHumanMap.put("update", (id, human) -> new Update(
            handler, Long.parseLong(id), new UsersUpdate(usersHandler, human, Long.parseLong(id))));
    }

    public CommandHandler(CollectionHandler handler, UsersCollectionHandler usersHandler) {
        this.usersHandler = usersHandler;
        this.handler = handler;
    }

    public Respond executeCommand(Command<Respond> command) {
        try {
            commands.add(command);
            return command.execute();
        } catch (Exception e) {
            e.printStackTrace();
            return new Respond("Something went wrong: " + e.getMessage());
        }
    }

    public Respond switchCommand(String command, User user, String arg, HumanBeing human) {
        if         (noArgMap.get(command) != null) return executeCommand(noArgMap.get(command).get());
        else if (guiNoArgMap.get(command) != null) return executeCommand(guiNoArgMap.get(command).get());
        else if (strMap.get(command)      != null) return executeCommand(strMap.get(command).apply(arg));
        else if (humanMap.get(command)    != null) return executeCommand(humanMap.get(command).apply(human));
        else if (userMap.get(command)     != null) return executeCommand(userMap.get(command).apply(user));
        else if (strUserMap.get(command)  != null) return executeCommand(strUserMap.get(command).apply(arg, user));
        else if (strHumanMap.get(command) != null) return executeCommand(strHumanMap.get(command).apply(arg, human));
        else return new Respond("No such command");
    }
}
