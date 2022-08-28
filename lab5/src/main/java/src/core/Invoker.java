package src.core;

import src.elements.*;
import src.commands.*;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.BiConsumer;
//import static java.util.Map.entry;

/** Class, which executes command due to it's name, and store it's objects */
public class Invoker {

    /** CollectionHandler object, needs to execute command */
    private CollectionHandler handler;

    /** List, used to store executed Command objects */
    private List<Command> commands = new ArrayList<>();

    /** Map, storing name of the command, and Runnable object to execute command with one argument */
    private Map<String, Runnable> runnableMap = new HashMap<>();
    {
        runnableMap.put("print_descending", () -> executeCommand(new PrintDescending(handler)));
        runnableMap.put("print_ascending",  () -> executeCommand(new PrintAscending(handler)));
        runnableMap.put("clear",            () -> executeCommand(new Clear(handler)));
        runnableMap.put("show",             () -> executeCommand(new Show(handler)));
        runnableMap.put("save",             () -> executeCommand(new Save(handler)));
        runnableMap.put("help",             () -> executeCommand(new Help(handler)));
        runnableMap.put("info",             () -> executeCommand(new Info(handler)));
        runnableMap.put("exit",             () -> executeCommand(new Exit(handler)));
    }

    /** Map, storing name of the command, and Consumer object to execute command with two argument */
    private Map<String, Consumer<HumanBeing>> map = new HashMap<>();
    {
        map.put("add",            x -> executeCommand(new Add(handler, x)));
        map.put("add_if_min",     x -> executeCommand(new AddIfMin(handler, x)));
        map.put("remove_greater", x -> executeCommand(new RemoveGreater(handler, x)));
        map.put("remove_lower",   x -> executeCommand(new RemoveLower(handler, x)));
    }

    /** Map, storing name of the command, and Consumer object to execute command with two argument */
    private Map<String, Consumer<String>> mapStr = new HashMap<>();
    {
        mapStr.put("remove_by_id",         x -> executeCommand(new RemoveById(handler, Long.parseLong(x))));
        mapStr.put("filter_contains_name", x -> executeCommand(new FilterContainsName(handler, x)));
        mapStr.put("execute_script",       x -> executeCommand(new ExecuteScript(handler, x, this)));
    }

    /** Map, storing name of the command, and BiConsumer object to execute command with three argument */
    private Map<String, BiConsumer<String, HumanBeing>> biMap = new HashMap<>();
    {
        biMap.put("update", (x, y) -> executeCommand(new Update(handler, Long.parseLong(x), y)));
    }

    /**
        Creates new Invoker object
        @param handler CollectionHandler object to execute commands
    */
    public Invoker(CollectionHandler handler) {
        this.handler = handler;
    }

    /**
        Executes current command and adds it's object to list commands
        @param command object to execute
    */
    public void executeCommand(Command command) {
        commands.add(command);
        command.execute();
    }

    /** 043B - 1100 0100 1011 1011
        Switches command due to it's name
        @param line array of strings, contains command name and optionl string
        @param human object to execute command
    */
    public void switchCommand(String[] line, HumanBeing human){
        Optional.ofNullable(runnableMap.get(line[0])).ifPresent(x -> x.run());
        Optional.ofNullable(mapStr.get(line[0])).ifPresent(x -> x.accept(line[1]));
        Optional.ofNullable(map.get(line[0])).ifPresent(x -> x.accept(human));
        Optional.ofNullable(biMap.get(line[0])).ifPresent(x -> x.accept(line[1], human));
    }

}
