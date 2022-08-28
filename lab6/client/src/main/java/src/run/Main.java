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

import util.*;


public class Main {

    public final static int SERVICE_PORT = 50006;

    public static void main(String[] args) {
        try {

            ConnectionHandler connection = new ConnectionHandler(
                DatagramChannel.open(), new InetSocketAddress(
                    InetAddress.getByName("localhost"), SERVICE_PORT));

            ProgrammHandler programm = new ProgrammHandler();

            CommandHandler handler = new CommandHandler(connection, programm);

            AbstractReader reader = new InputReader();

            System.out.println("Hello, now you can operate with collection of Human beings! Feel Power!!!");
            String[] line;
            while (true) {
                line = reader.getFirstLine();
                handler.switchCommand(
                    line[0], line[1], CheckInput.checkElemArg(line[0]) ? reader.getHumanBeing() : null);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
