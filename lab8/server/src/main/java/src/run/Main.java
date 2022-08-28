
package src.run;

import java.io.*;

import java.net.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import java.nio.channels.DatagramChannel;
import java.nio.ByteBuffer;

import java.util.PriorityQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.Scanner;

import src.core.model.commands.commonCommands.Save;
import src.core.model.handlers.*;
import src.core.model.elements.*;
import src.core.controller.*;
import src.core.view.*;

import src.util.*;


public class Main{
    public static int SERVICE_PORT;
    private static String URL;
    private static String LOGIN;
    private static String PASSWORD;

    static ExecutorService receiverService = Executors.newFixedThreadPool(5);

    static ExecutorService responderService = Executors.newFixedThreadPool(10);

    private static DatabaseHandler database;

    private static UsersCollectionHandler usersHandler;

    private static DatagramChannel serverChannel;

    private static Thread save;

    public static void main(String[] args) {
        if (!initialize(System.getenv().get("FILENAME"), args)) return;

        System.out.println(
            "Server is working now!" + System.lineSeparator() +
               "To stop it and save collection type \"save\"");

        try{
            database = new DatabaseHandler(URL, LOGIN, PASSWORD);

            usersHandler = new UsersCollectionHandler(database.getConnection());

            CollectionHandler handler =
                new CollectionHandler(database.readFromDB(new PriorityQueue<HumanBeing>()));

            CommandHandler invoker = new CommandHandler(handler, usersHandler);

            serverChannel =
                DatagramChannel.open().bind(
                    new InetSocketAddress(SERVICE_PORT));

            save = new SaveThread(handler, usersHandler, database, invoker, serverChannel, receiverService);
            save.start();

            AcceptConnection receiver =
                new AcceptConnection(
                    serverChannel, ByteBuffer.allocate(1024));

            SendRespond responder= new SendRespond(serverChannel);

            while (!receiverService.isShutdown()) {

                Future<SocketAddress> future = receiverService.submit(() -> receiver.receiveData());
                while (!future.isDone()) { }
                responderService.execute(
                    new ClientHandler(invoker, usersHandler, responder, future.get(), receiver.getObject()));
            }
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            finalaze();
        }
    }


    public static void finalaze() {
        receiverService.shutdownNow();
        responderService.shutdownNow();
        try {
            serverChannel.close();
            database.close();
        } catch (Exception e) { e.printStackTrace(); }
        usersHandler.closeStatements();
        save.interrupt();
        System.exit(0);
    }

    public static boolean initialize(String fileName, String[] args) {
        String[] result = new String[5];
        try (Scanner scanner = new Scanner(new File(fileName)).useDelimiter(":")) {
            if (args.length != 1) return false;
            SERVICE_PORT = Integer.parseInt(args[0]);
            if (SERVICE_PORT < 0) return false;

            for (int i = 0; i < 5; i++) result[i] = scanner.next();
            URL = "jdbc:postgresql://" + result[0] + ":" + result[1] + "/" + result[2];
            LOGIN = result[3]; PASSWORD = result[4];
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
