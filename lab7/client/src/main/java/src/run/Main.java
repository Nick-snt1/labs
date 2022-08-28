package src.run;

import java.io.*;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.*;
import java.nio.channels.DatagramChannel;
import java.nio.ByteBuffer;

import java.util.concurrent.TimeUnit;
import src.core.handlers.*;
import src.core.readers.*;
import src.core.CoreThread;

import src.util.*;


public class Main {

    public static int SERVICE_PORT;

    public static void main(String[] args) {
        if (!initialize(args)) return;

        try {

            ConnectionHandler connection = new ConnectionHandler(
                DatagramChannel.open(), new InetSocketAddress(
                    InetAddress.getByName("localhost"), SERVICE_PORT));

            CommandHandler handler = new CommandHandler(connection, new ProgrammHandler());

            CoreThread coreThread = new CoreThread(handler);

            handler.setThread(coreThread);

            AuthorizationHandler auth = new AuthorizationHandler(handler);

            User user;
            while (true) {
                user = auth.getUser();
                coreThread.setUser(user);
                if (coreThread.getState() == Thread.State.NEW) {
                    coreThread.start();
                } else {
                    coreThread.setIsActive(true);
                    coreThread.run();
                }
                coreThread.join();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static boolean initialize(String[] args) {
        try {
            if (args.length != 1) return false;
            SERVICE_PORT = Integer.parseInt(args[0]);
            if (SERVICE_PORT < 0) return false;
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
