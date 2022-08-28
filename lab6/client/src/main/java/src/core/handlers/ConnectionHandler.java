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

import util.*;

public class ConnectionHandler {

    DatagramChannel channel;

    DatagramSocket socket;

    InetSocketAddress target;

    public ConnectionHandler(DatagramChannel channel, InetSocketAddress target) {
        this.channel = channel;
        this.socket = channel.socket();
        this.target = target;
    }

    public void connect(DTO sendingData) {
        try {
            configureBlocking(false);

            safeSend(sendingData);

            System.out.println("Waiting for a server to respond...");

            configureBlocking(true);

            Integer size = (Integer) toObject(getRespond(1024));

            System.out.println(((DTO) bigDataRespond(size)).getGeneral());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Object bigDataRespond(int size) throws IOException, ClassNotFoundException {
        int ammountOfPackets = size/64000 + (size%64000 == 0 ? 0 : 1);
        byte[] result = new byte[0];
        for (int i = 0; i < ammountOfPackets; i++)
            result = concat(result, getRespond((Integer) toObject(getRespond(1024))));
        return toObject(result);
    }

    private byte[] concat(byte[] first, byte[] second) {
        byte[] both = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, both, first.length, second.length);
        return both;
    }

    private byte[] getRespond(int size) throws IOException, ClassNotFoundException {
        byte[] dataBuffer = new byte[size];
        DatagramPacket packet = new DatagramPacket(dataBuffer, dataBuffer.length);
        socket.receive(packet);
        return packet.getData();
    }

    private void safeSend(Serializable object) throws IOException, InterruptedException {
        SocketAddress check = null;
        int time = 0;
        do {
            channel.send(ByteBuffer.wrap(objectToBytes(object)), target);
            TimeUnit.MILLISECONDS.sleep(50);
            if ((check = channel.receive(ByteBuffer.allocate(1))) == null) {
                System.out.println("Server isn't responding. Wait for 5 sec");
                time += 5;
                TimeUnit.SECONDS.sleep(5);
                if (time == 30) {
                    Console console = System.console();
                    while (true) {
                        System.out.println("Wait for more 30 seconds? (y/n)>>");
                        String word = console.readLine();
                        if (word != null && word.trim().toLowerCase().equals("y")) {
                            time = 0;
                            break;
                        } else if (word != null && word.trim().toLowerCase().equals("n")) {
                            channel.close();
                            System.exit(0);
                        } else {
                            System.out.println("Incorrect input");
                        }
                    }
                }
            }
        } while (check == null);
        System.out.println("Send successfully!");
    }

    private static byte[] objectToBytes(Serializable object) throws IOException {
        ByteArrayOutputStream bs = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(bs)) {
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
