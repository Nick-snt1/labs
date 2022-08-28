package src.core.controller;

import java.io.Console;
import java.nio.channels.DatagramChannel;
import java.util.concurrent.ExecutorService;

import src.core.model.handlers.*;
import src.core.model.commands.commonCommands.Save;

public class SaveThread extends Thread {
    private final CollectionHandler collection;

    private final CommandHandler commands;

    private final DatagramChannel connection;

    private final UsersCollectionHandler usersHandler;

    private final DatabaseHandler database;

    private final ExecutorService service;

    public SaveThread(CollectionHandler collection, UsersCollectionHandler usersHandler ,
                      DatabaseHandler database, CommandHandler commands,
                      DatagramChannel connection, ExecutorService service) {
        this.collection = collection;
        this.commands = commands;
        this.connection = connection;
        this.database = database;
        this.usersHandler = usersHandler;
        this.service = service;
    }

    @Override
    public void run() {
        Console console = System.console();
        while (true) {
            String exitWord = console.readLine();
            if (exitWord != null && exitWord.trim().equals("save")) {
                try {
                    service.shutdown();
                    usersHandler.closeStatements();
                    database.close();
                    connection.close();
                } catch (Exception e) {
                } finally {
                    String message = commands.executeCommand(new Save(collection));
                    System.out.println(message);
                    System.exit(0);
                }
            }
        }
    }
}
