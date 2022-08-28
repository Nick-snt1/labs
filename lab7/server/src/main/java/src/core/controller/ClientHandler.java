package src.core.controller;

import java.net.*;

import src.core.view.*;
import src.core.model.handlers.*;
import java.nio.channels.DatagramChannel;

import src.util.*;

public class ClientHandler implements Runnable {
    private CommandHandler invoker;

    private SendRespond responder;

    private UsersCollectionHandler handler;

    private Object obj;

    private SocketAddress clientAddr;

    public ClientHandler(CommandHandler invoker,UsersCollectionHandler handler, SendRespond responder, SocketAddress clientAddr, Object obj) {
        this.invoker = invoker;
        this.handler = handler;
        this.responder = responder;
        this.clientAddr = clientAddr;
        this.obj = obj;
    }

    @Override
    public void run() {
        try {
            DTOConverter data = new DTOConverter((DTO) obj);

            Respond respond = new Respond( !data.getCommand().equals("authorization")
                && !data.getCommand().equals("registration") && !handler.checkAuth(data.getUser())
                ? "Authorization failure, try to reconnect"
                    : invoker.switchCommand(data.getCommand(), data.getUser(), data.getArg(), data.getHuman()));

            responder.send(respond, (InetSocketAddress) clientAddr);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
//new SendRespond(channel).send(sendingData, (InetSocketAddress) clientAddr);
