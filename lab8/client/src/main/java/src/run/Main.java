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

import javax.swing.*;

import src.core.gui.*;

import src.util.*;


public class Main {

    public static int SERVICE_PORT;

    public static int UPDATE_PORT;

    public static String mode = "console";

    public static void main(String[] args) {
        if (!initialize(args)) return;

        try {

            ConnectionHandler connection = new ConnectionHandler(
                DatagramChannel.open(), new InetSocketAddress(
                    InetAddress.getByName("localhost"), SERVICE_PORT));

            CommandHandler handler = new CommandHandler(connection, new ProgrammHandler());

            if (mode.equals("gui")) {
                SwingUtilities.invokeLater(new GUI(handler, connection, UPDATE_PORT)); 
            } else {
                CoreThread coreThread = new CoreThread(handler);

                handler.setThread(coreThread);

                AuthorizationHandler auth = new AuthorizationHandler(handler);

                while (true) {
                    coreThread.setUser(auth.getUser());
                    coreThread.run();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static boolean initialize(String[] args) {
        try {
            if (args.length != 2 && args.length != 1 && args.length != 3) return false;
            SERVICE_PORT = Integer.parseInt(args[0]);

            if (args.length == 2 || args.length == 3) UPDATE_PORT = Integer.parseInt(args[1]);
            if (args.length == 3) mode = args[2];

            if ((!mode.equals("gui") && !mode.equals("console"))
                || SERVICE_PORT < 0 || (args.length == 2 && UPDATE_PORT < 0)) return false;

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
