package src.core.commands;

import src.core.handlers.*;

/** Class, makes object from command */
public class Exit implements Command {

    /** Handler to execute command */
    private ProgrammHandler programm;

    private ConnectionHandler connection;
    /**
        Creates new object
        @param collectionHandler handler to execute command
    */
    public Exit(ProgrammHandler programm, ConnectionHandler connection) {
        this.programm = programm;
        this.connection = connection;
    }

    @Override
    public void execute() {
        programm.exit(connection);
    }
}
