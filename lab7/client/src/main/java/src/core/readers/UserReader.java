package src.core.readers;

import java.io.Console;
import java.util.*;

import src.util.*;

public abstract class UserReader {
    protected static Console console = System.console();

    private static final Map<String, String> map = new HashMap<String, String>() {{
        put("y"   , "authorization");
        put("n"   , "registration");
        put("quit", "quit");
    }};

    public static String getCommand() {
        String command;
        do {
            System.out.print("Already have an account? (y/n) ... or type 'quit' to close programm"
                + System.lineSeparator() + ">> ");
            if ((command = console.readLine()) != null) command = command.trim().toLowerCase();
        } while (!CheckInput.checkCommand(command));
        return map.get(command);
    }

    protected abstract String getLogin();

    protected abstract String getPassword();

    public User getUser() {
        return new User(getLogin(), getPassword());
    }
}
