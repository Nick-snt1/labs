package src.core.commands;

import src.core.handlers.*;
import src.util.*;

public class Exit implements Command<Respond> {

    private ProgrammHandler programm;

    private ConnectionHandler connection;

    public Exit(ProgrammHandler programm, ConnectionHandler connection) {
        this.programm = programm;
        this.connection = connection;
    }

    @Override
    public Respond execute() {
        return programm.exit(connection);
    }
}
