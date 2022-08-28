package src.core.view;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.net.InetSocketAddress;

import java.util.logging.*;
import java.util.Arrays;

import java.io.*;

public class SendRespond {
    private final DatagramSocket serverSocket;

    private static final Logger LOGGER = Logger.getLogger(SendRespond.class.getName());

    public SendRespond(DatagramSocket serverSocket) {
        LOGGER.info("New SendRespond created");
        this.serverSocket = serverSocket;
    }

    public void send(Serializable sendingData, InetSocketAddress clientAddr) throws IOException {

        byte[] send = objectToBytes(sendingData);

        byte[] size = objectToBytes(send.length);

        LOGGER.log(Level.INFO, "Size of respond send to the client ({0})", send.length);

        serverSocket.send(
            new DatagramPacket(
                size, size.length, clientAddr.getAddress(), clientAddr.getPort() ));

        LOGGER.log(Level.INFO, "Respond send to the client");

        bigDataSend(send, clientAddr);

        LOGGER.info("Method send executed");
    }

    private void bigDataSend(byte[] data, InetSocketAddress clientAddr) throws IOException {
        int packets = data.length/64000 + (data.length%64000 == 0 ? 0 : 1);
        int from = 0, to = 64000;
        for (int i = 0; i < packets; i++) {
            if (i == packets - 1) to = data.length;

            byte[] send = Arrays.copyOfRange(data, from, to);
            byte[] size = objectToBytes(send.length);

            serverSocket.send(
                new DatagramPacket(
                    size, size.length, clientAddr.getAddress(), clientAddr.getPort() ));

            serverSocket.send(
                new DatagramPacket(
                    send, send.length, clientAddr.getAddress(), clientAddr.getPort() ));

            from += 64000; to += 64000;
        }
        LOGGER.log(Level.INFO, "" + packets + (packets == 1 ? " packet ": " packets ") + "send to the client");
    }

    private byte[] objectToBytes(Serializable object) throws IOException {
        ByteArrayOutputStream bs = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(bs)) {
            oos.writeObject(object);
            LOGGER.info("Object were transfered to bytes");
            return bs.toByteArray();
        }
    }
}
