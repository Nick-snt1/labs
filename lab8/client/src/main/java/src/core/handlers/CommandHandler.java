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

    private List<Command<Respond>> commands = new ArrayList<>();

    private Map<String, Supplier<                Command<Respond>>> runnableMap = new HashMap<>();
    private Map<String, BiFunction<String, User, Command<Respond>>> map         = new HashMap<>();
    private Map<String, Function<DTO,            Command<Respond>>> map1        = new HashMap<>();
    {
        runnableMap.put("quit",   () -> new Exit(programm, connection));
        runnableMap.put("exit",   () -> new StopThread(programm, t));
        map1.put("connect",        x -> new Connect(connection, x));
        map .put("execute_script", (x,y) -> new ExecuteScript(programm, x, y, this));
        map .put("gui_execute_script", (x,y) -> new GUIExecuteScript(programm, x, y, this));
    }

    public CommandHandler(ConnectionHandler connection, ProgrammHandler programm) {
        this.programm = programm;
        this.connection = connection;
    }

    public void setThread(CoreThread t) { this.t = t; }

    public Respond executeCommand(Command<Respond> command) {
        try {
            commands.add(command);
            return command.execute();
        } catch (Exception e) {
            return new Respond(e.getMessage());
        }
    }

    public Respond switchCommand(String command, User user, String arg, String[] human) {
        Supplier<                Command<Respond>> x = runnableMap.get(command);
        BiFunction<String, User, Command<Respond>> y = map.get(command);
        if (x != null)      return executeCommand(x.get());
        else if (y != null) return executeCommand(y.apply(arg, user));
        else                return executeCommand(map1.get("connect").apply(new DTO(command, user, arg, human)));
    }
}
