package src.core.controller;

import java.io.Console;
import java.nio.channels.DatagramChannel;
import src.core.model.handlers.*;
import src.core.model.commands.Save;
import java.util.logging.*;

public class SaveThread extends Thread {
    private final CollectionHandler collection;

    private final CommandHandler commands;

    private final DatagramChannel connection;

    private static final Logger LOGGER = Logger.getLogger(SaveThread.class.getName());

    public SaveThread(CollectionHandler collection, CommandHandler commands, DatagramChannel connection) {
        LOGGER.info("New SaveThread created");
        this.collection = collection;
        this.commands = commands;
        this.connection = connection;
    }

    @Override
    public void run() {
        LOGGER.info("SaveThread is running");
        Console console = System.console();
        while (true) {
            String exitWord = console.readLine();
            if (exitWord != null && exitWord.trim().equals("save")) {
                try {
                    connection.disconnect();
                    LOGGER.info("Connection terminated");
                } catch (Exception e) {
                    LOGGER.log(Level.WARNING, e.getMessage());
                }
                String message = commands.executeCommand(new Save(collection));
                System.out.println(message);
                LOGGER.info("Programm terminated");
                System.exit(0);
            }
        }
    }
}
