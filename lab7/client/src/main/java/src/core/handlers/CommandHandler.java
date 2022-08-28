package src.core.handlers;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import src.core.CoreThread;

import src.util.*;
import src.core.commands.*;

public class CommandHandler {

    private ProgrammHandler programm;

    private CoreThread t;

    private ConnectionHandler connection;

    private List<Command> commands = new ArrayList<>();

    private Map<String, Supplier<Command<String>>> runnableMap = new HashMap<>();
    private Map<String, BiFunction<String, User, Command<String>>> map = new HashMap<>();
    private Map<String, Function<DTO, Command<String>>> map1   = new HashMap<>();
    {
        runnableMap.put("quit",   () -> new Exit(programm, connection));
        runnableMap.put("exit",   () -> new StopThread(programm, t));
        map1.put("connect",        x -> new Connect(connection, x));
        map .put("execute_script", (x,y) -> new ExecuteScript(programm, x, y, this));
    }

    public CommandHandler(ConnectionHandler connection, ProgrammHandler programm) {
        this.programm = programm;
        this.connection = connection;
    }

    public void setThread(CoreThread t) { this.t = t; }

    public String executeCommand(Command<String> command) {
        try {
            commands.add(command);
            return command.execute();
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String switchCommand(String command, User user, String arg, String[] human) {
        Supplier<Command<String>> x                 = runnableMap.get(command);
        BiFunction<String, User, Command<String>> y = map.get(command);
        if (x != null)      return executeCommand(x.get());
        else if (y != null) return executeCommand(y.apply(arg, user));
        else                return executeCommand(map1.get("connect").apply(new DTO(command, user, arg, human)));
    }
}
