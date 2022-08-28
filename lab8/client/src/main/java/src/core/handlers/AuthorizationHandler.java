package src.core.handlers;

import java.util.function.Supplier;
import java.util.Map;
import java.util.HashMap;

import src.core.readers.*;
import src.util.*;

public class AuthorizationHandler {
    private final CommandHandler handler;

    private final Map<String, Supplier<User>> map = new HashMap<>();
    {
        map.put("registration",  () -> new RegUserReader().getUser());
        map.put("authorization", () -> new AuthUserReader().getUser());
    }

    public AuthorizationHandler(CommandHandler handler) {
        this.handler = handler;
    }

    public User getUser() {
        String command, respond;
        User user;
        do {
            command = UserReader.getCommand();
            user    = map.get(command) == null ? null : map.get(command).get();
            respond = handler.switchCommand(command, user, null, null).getRespond();
            System.out.println(!respond.equals("OK")
                ? respond : command.equals("registration")
                    ? "Registration complete!" : "Authorization complete!");
        } while (!(command.equals("authorization") && respond.equals("OK")));
        return user;
    }
}
