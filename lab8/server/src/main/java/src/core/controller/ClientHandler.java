package src.core.controller;

import java.net.*;
import java.util.*;

import src.core.view.*;
import src.core.model.handlers.*;
import java.nio.channels.DatagramChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import src.util.*;

public class ClientHandler implements Runnable {
    private CommandHandler invoker;

    private SendRespond responder;

    private UsersCollectionHandler handler;

    private Object obj;

    private SocketAddress clientAddr;

    private static HashSet<String> commands = new HashSet<>(Arrays.asList(
        "add", "add_if_min", "remove_by_id", "remove_lower", "remove_greater", "update", "clear"));

    private static HashSet<InetSocketAddress> set = new HashSet<>();

    public ClientHandler(CommandHandler invoker, UsersCollectionHandler handler, SendRespond responder, SocketAddress clientAddr, Object obj) {
        this.invoker = invoker;
        this.handler = handler;
        this.responder = responder;
        this.clientAddr = clientAddr;
        this.obj = obj;
    }

    public static synchronized void addToSet(InetSocketAddress clientAddr, Integer port) {
        ClientHandler.set.add(new InetSocketAddress(clientAddr.getAddress(), port));
    }

    public static synchronized void removeFromSet(InetSocketAddress clientAddr, Integer port) {
        ClientHandler.set.remove(new InetSocketAddress(clientAddr.getAddress(), port));
    }

    public static synchronized void sendUpdates(SendRespond responder, Respond respond) {
        ClientHandler.set.forEach(x -> responder.send(respond, x));
    }

    @Override
    public void run() {
        try {
            DTOConverter data = new DTOConverter((DTO) obj);

            Respond respond = !data.getCommand().equals("authorization")
                && !data.getCommand().equals("registration") && !handler.checkAuth(data.getUser())
                ? new Respond("Authorization failure, try to reconnect")
                    : invoker.switchCommand(data.getCommand(), data.getUser(), data.getArg(), data.getHuman());

            if (data.getCommand().equals("authorization")
                && !(data.getArg() == null)
                    && respond.getRespond().equals("OK"))
                        addToSet((InetSocketAddress) clientAddr, Integer.valueOf(data.getArg()));


            if (data.getCommand().equals("guiExit"))
                removeFromSet((InetSocketAddress) clientAddr, Integer.valueOf(data.getArg()));

            if (commands.contains(data.getCommand())) sendUpdates(responder, respond);

            responder.send(respond, (InetSocketAddress) clientAddr);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
