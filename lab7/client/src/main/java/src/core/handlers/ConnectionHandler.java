package src.core.handlers;

import java.io.*;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.*;
import java.nio.channels.DatagramChannel;
import java.nio.ByteBuffer;
import java.util.concurrent.TimeUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

import src.util.*;

public class ConnectionHandler {

    DatagramChannel channel;

    DatagramSocket socket;

    InetSocketAddress target;

    public ConnectionHandler(DatagramChannel channel, InetSocketAddress target) {
        this.channel = channel;
        this.socket = channel.socket();
        this.target = target;
    }

    public String connect(DTO sendingData) {
        try {
            configureBlocking(false);

            safeSend(sendingData);

            configureBlocking(true);

            Integer size = (Integer) toObject(getRespond(1024));

            return ((Respond) bigDataRespond(size)).getRespond(); ///!!!!Rename one of the methods

        } catch (Exception e) {
            return e.getMessage();
        }
    }

    private Object bigDataRespond(int size) throws IOException, ClassNotFoundException {
        int packets = size/64000 + (size%64000 == 0 ? 0 : 1);
        byte[][] result = new byte[packets][];
        for (int i = 0; i < packets; i++) result[i] = getRespond(64000);
        return toObject(concat(result));
    }

    private byte[] concat(byte[][] array) {
        byte[] result = new byte[array.length * 64000];
        for (int i = 0; i < array.length * 64000; i++) result[i] = array[i/64000][i%64000];
        return result;
    }

    private byte[] getRespond(int size) throws IOException, ClassNotFoundException {
        byte[] dataBuffer = new byte[size];
        DatagramPacket packet = new DatagramPacket(dataBuffer, dataBuffer.length);
        socket.receive(packet);
        return packet.getData();
    }

    private void safeSend(Serializable object) throws IOException, InterruptedException {
        SocketAddress check = null;
        int time = 30;
        do {
            channel.send(ByteBuffer.wrap(objectToBytes(object)), target);
            TimeUnit.MILLISECONDS.sleep(50);
            if ((check = channel.receive(ByteBuffer.allocate(1))) == null) {
                time = waitForReconnect(time);
            }
        } while (check == null);
    }

    private int waitForReconnect(int time) throws IOException, InterruptedException {
        System.out.println("Server isn't responding. Wait for " + time +  " sec");
        TimeUnit.SECONDS.sleep(5);
        time -= 5;
        if (time == 0) {
            Console console = System.console();
            while (true) {
                System.out.print("Wait for more 30 seconds? (y/n)>> ");
                String word = console.readLine();
                if (word != null && word.trim().toLowerCase().equals("y")) {
                    time = 30; break;
                } else if (word != null && word.trim().toLowerCase().equals("n")) {
                    channel.close(); System.exit(0);
                } else {
                    System.out.println("Incorrect input");
                }
            }
        }
        return time;
    }

    private static byte[] objectToBytes(Serializable object) throws IOException {
        try (ByteArrayOutputStream bs = new ByteArrayOutputStream();
               ObjectOutputStream oos = new ObjectOutputStream(bs)) {
            oos.writeObject(object);
            return bs.toByteArray();
        }
    }

    private Object toObject(byte[] array) throws IOException, ClassNotFoundException {
        return new ObjectInputStream(new ByteArrayInputStream(array)).readObject();
    }

    public void close() throws IOException { channel.close(); }

    private void configureBlocking(boolean conf) throws IOException {
        channel.configureBlocking(conf);
    }
}
