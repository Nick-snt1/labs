package src.core.model.handlers;

import src.core.model.commands.*;
import src.core.model.elements.*;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.logging.*;

public class CommandHandler {

    private CollectionHandler handler;

    private List<Command> commands = new ArrayList<>();

    private static final Logger LOGGER = Logger.getLogger(CommandHandler.class.getName());

    private Map<String, Supplier<Command>> runnableMap = new HashMap<>();
    {
        runnableMap.put("print_descending", () -> new PrintDescending(handler));
        runnableMap.put("print_ascending",  () -> new PrintAscending(handler));
        runnableMap.put("clear",            () -> new Clear(handler));
        runnableMap.put("show",             () -> new Show(handler));
        runnableMap.put("save",             () -> new Save(handler));
        runnableMap.put("help",             () -> new Help(handler));
        runnableMap.put("info",             () -> new Info(handler));
    }

    private Map<String, Function<HumanBeing, Command>> map = new HashMap<>();
    {
        map.put("add",            x -> new Add(handler, x));
        map.put("add_if_min",     x -> new AddIfMin(handler, x));
        map.put("remove_greater", x -> new RemoveGreater(handler, x));
        map.put("remove_lower",   x -> new RemoveLower(handler, x));
    }

    private Map<String, Function<String, Command>> mapStr = new HashMap<>();
    {
        mapStr.put("remove_by_id",         x -> new RemoveById(handler, Long.parseLong(x)));
        mapStr.put("filter_contains_name", x -> new FilterContainsName(handler, x));
    }

    private Map<String, BiFunction<String, HumanBeing, Command>> biMap = new HashMap<>();
    {
        biMap.put("update", (x, y) -> new Update(handler, Long.parseLong(x), y));
    }

    public CommandHandler(CollectionHandler handler) {
        LOGGER.info("New CommandHandler was created");
        this.handler = handler;
    }

    public String executeCommand(Command command) {
        commands.add(command);
        return command.execute();
    }

    public String switchCommand(String command, String arg, HumanBeing human) {
        LOGGER.log(Level.INFO, "Method switchCommand executed with command {0}, arg {1}", new Object[] {command, arg});
        Supplier<Command>                       x = runnableMap.get(command);
        Function<String, Command>               y = mapStr.get(command);
        Function<HumanBeing, Command>           z = map.get(command);
        BiFunction<String, HumanBeing, Command> k = biMap.get(command);
        if      (x != null) return executeCommand(x.get());
        else if (y != null) return executeCommand(y.apply(arg));
        else if (z != null) return executeCommand(z.apply(human));
        else if (k != null) return executeCommand(k.apply(arg, human));
        else return "No such command";
    }

}
