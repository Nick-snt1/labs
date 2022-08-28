package src.run;

import java.io.*;

import java.net.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import java.nio.channels.DatagramChannel;
import java.nio.ByteBuffer;

import java.util.logging.*;
import java.util.PriorityQueue;
import java.util.concurrent.TimeUnit;

import src.core.model.commands.Save;
import src.core.model.handlers.*;
import src.core.model.elements.*;
import src.core.controller.*;
import src.core.view.*;

import util.*;


public class Main{
    public final static int SERVICE_PORT = 50006;

    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        LOGGER.info("Server started");

        System.out.println(
            "Server is working now!" + System.lineSeparator() +
            "To stop it and save collection type \"save\"");

        String fileName = System.getenv().get("FILENAME");


        CollectionHandler handler =
            new CollectionHandler(
                XMLHandler.readXML(fileName, new PriorityQueue<HumanBeing>()), fileName);

        CommandHandler invoker = new CommandHandler(handler);

        try{
            DatagramChannel serverChannel =
                DatagramChannel.open().bind(
                    new InetSocketAddress(SERVICE_PORT));

            Thread save = new SaveThread(handler, invoker, serverChannel);
            save.start();

            AcceptConnection connection =
                new AcceptConnection(
                    serverChannel, ByteBuffer.allocate(1024));

            SendRespond respond = new SendRespond(serverChannel.socket());

            while (true) {
                serverChannel.configureBlocking(false);

                SocketAddress clientAddr = connection.receiveData();

                DTOConverter data = new DTOConverter((DTO) connection.getObject());

                DTO sendingData = new DTO(
                    invoker.switchCommand(data.getCommand(), data.getArg(), data.getHuman()));

                serverChannel.configureBlocking(true);

                respond.send(sendingData, (InetSocketAddress) clientAddr);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            String message = invoker.executeCommand(new Save(handler));
            System.out.println(message);
            LOGGER.log(Level.WARNING, "Exception occured: {0}", e.getMessage());
            LOGGER.info("Collection saved and work stoped");
            System.exit(0);
        }
    }
}
