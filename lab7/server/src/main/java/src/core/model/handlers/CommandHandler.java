package src.core.model.handlers;

import src.core.model.commands.Command;
import src.core.model.commands.collectionCommands.*;
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

public class CommandHandler {

    private CollectionHandler handler;

    private UsersCollectionHandler usersHandler;

    private List<Command> commands = new ArrayList<>();

    private Map<String, Supplier<Command<String>>>                       noArgMap = new HashMap<>();
    private Map<String, Function<HumanBeing, Command<String>>>           humanMap = new HashMap<>();
    private Map<String, Function<String, Command<String>>>               strMap = new HashMap<>();
    private Map<String, Function<User, Command<String>>>                 userMap = new HashMap<>();
    private Map<String, BiFunction<String, User, Command<String>>>       strUserMap = new HashMap<>();
    private Map<String, BiFunction<String, HumanBeing, Command<String>>> strHumanMap = new HashMap<>();
    {
        noArgMap.put("print_descending", () -> new PrintDescending(handler));
        noArgMap.put("print_ascending",  () -> new PrintAscending(handler));
        noArgMap.put("save",             () -> new Save(handler));
        noArgMap.put("help",             () -> new Help(handler));
        noArgMap.put("show",             () -> new Show(handler));

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

    public String executeCommand(Command<String> command) {
        try {
            commands.add(command);
            return command.execute();
        } catch (Exception e) {
            return "Something went wrong: " + e.getMessage();
        }
    }

    public String switchCommand(String command, User user, String arg, HumanBeing human) {
        Supplier<Command<String>>                       a = noArgMap.get(command);
        Function<String,Command<String>>                b = strMap.get(command);
        Function<HumanBeing, Command<String>>           c = humanMap.get(command);
        Function<User, Command<String>>                 d = userMap.get(command);
        BiFunction<String, User, Command<String>>       e = strUserMap .get(command);
        BiFunction<String, HumanBeing, Command<String>> f = strHumanMap.get(command);
        if      (a != null) return executeCommand(a.get());
        else if (b != null) return executeCommand(b.apply(arg));
        else if (c != null) return executeCommand(c.apply(human));
        else if (d != null) return executeCommand(d.apply(user));
        else if (e != null) return executeCommand(e.apply(arg, user));
        else if (f != null) return executeCommand(f.apply(arg, human));
        else return "No such command";
    }

}
/*

Map.ofEntries(
    entry("update", (x, y) -> executeCommand(new Update(handler, Long.parseLong(x), y)))
);
Map.ofEntries(
    entry("remove_by_id", x -> executeCommand(new RemoveById(handler, Long.parseLong(x)))),
    entry("filter_contains_name", x -> executeCommand(new FilterContainsName(handler, x))),
    entry("execute_script", x -> executeCommand(new ExecuteScript(handler, x, this)))
);

Map.ofEntries(
    entry("add", x -> executeCommand(new Add(handler, x))),
    entry("add_if_min", x -> executeCommand(new AddIfMin(handler, x))),
    entry("remove_greater", x -> executeCommand(new RemoveGreater(handler, x))),
    entry("remove_lower", x -> executeCommand(new RemoveLower(handler, x)))
);

Map.ofEntries(
    entry("print_descending", () -> executeCommand(new PrintDescending(handler))),
    entry("print_ascending", () -> executeCommand(new PrintAscending(handler))),
    entry("clear", () -> executeCommand(new Clear(handler))),
    entry("show", () -> executeCommand(new Show(handler))),
    entry("save", () -> executeCommand(new Save(handler))),
    entry("help", () -> executeCommand(new Help(handler))),
    entry("info", () -> executeCommand(new Info(handler))),
    entry("exit", () -> executeCommand(new Exit(handler)))
);
*/
