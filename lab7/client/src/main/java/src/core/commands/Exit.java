package src.core.commands;

import src.core.handlers.*;

public class Exit implements Command<String> {

    private ProgrammHandler programm;

    private ConnectionHandler connection;

    public Exit(ProgrammHandler programm, ConnectionHandler connection) {
        this.programm = programm;
        this.connection = connection;
    }

    @Override
    public String execute() {
        return programm.exit(connection);
    }
}
