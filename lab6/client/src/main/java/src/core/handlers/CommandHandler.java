package src.core.handlers;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;

import util.*;
import src.core.commands.*;

public class CommandHandler {

    private ProgrammHandler programm;

    private ConnectionHandler connection;

    private List<Command> commands = new ArrayList<>();

    private Map<String, Supplier<Command>> runnableMap = new HashMap<>();
    private Map<String, Function<String, Command>> map = new HashMap<>();
    private Map<String, Function<DTO, Command>> map1 = new HashMap<>();
    {
        runnableMap.put("exit", () -> new Exit(programm, connection));
        map .put("execute_script", x -> new ExecuteScript(programm, x, this));
        map1.put("connect", x -> new Connect(connection, x));
    }

    public CommandHandler(ConnectionHandler connection, ProgrammHandler programm) {
        this.programm = programm;
        this.connection = connection;
    }

    public void executeCommand(Command command) {
        commands.add(command);
        command.execute();
    }

    public void switchCommand(String command, String arg, String[] human) {
        Supplier<Command> x         = runnableMap.get(command);
        Function<String, Command> y = map.get(command);
        if (x != null)      executeCommand(x.get());
        else if (y != null) executeCommand(y.apply(arg));
        else                executeCommand(map1.get("connect").apply(new DTO(command, arg, human)));
    }
}
