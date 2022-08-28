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
import src.core.gui.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;

import src.util.*;

import src.run.Main;

public class ConnectionHandler {

    DatagramChannel channel;

    DatagramSocket socket;

    DatagramSocket updateSocket;

    InetSocketAddress target;

    public ConnectionHandler(DatagramChannel channel, InetSocketAddress target) {
        this.channel = channel;
        this.socket = channel.socket();
        this.target = target;
    }

    public Respond connect(DTO sendingData) {
        try {
            configureBlocking(false);

            safeSend(sendingData);

            configureBlocking(true);

            Integer size = (Integer) toObject(getRespond(1024, socket));

            return (Respond) bigDataRespond(size, socket);

        } catch (Exception e) {
            return new Respond(e.getMessage());
        }
    }

    public void setUpdateSocket(int port) {
        try {
            updateSocket = new DatagramSocket(port);
        } catch (Exception e) { }
    }

    public Respond receiveUpdate() {
        try {
            Integer size = (Integer) toObject(getRespond(1024, updateSocket));
            return (Respond) bigDataRespond(size, updateSocket);
        } catch (Exception e) { return new Respond(e.getMessage()); }
    }

    private Object bigDataRespond(int size, DatagramSocket socket) throws IOException, ClassNotFoundException {
        int packets = size/64000 + (size%64000 == 0 ? 0 : 1);
        byte[][] result = new byte[packets][];
        for (int i = 0; i < packets; i++) result[i] = getRespond(64000, socket);
        return toObject(concat(result));
    }

    private byte[] concat(byte[][] array) {
        byte[] result = new byte[array.length * 64000];
        for (int i = 0; i < array.length * 64000; i++) result[i] = array[i/64000][i%64000];
        return result;
    }

    private byte[] getRespond(int size, DatagramSocket socket) throws IOException, ClassNotFoundException {
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
                time = Main.mode.equals("gui") ? guiWaitForReconnect(time) : waitForReconnect(time);
            }
        } while (check == null);
    }

    private int guiWaitForReconnect(int time) throws IOException, InterruptedException {
        if (time == 30) new WaitFrame(time); else WaitFrame.update(time);
        TimeUnit.SECONDS.sleep(5);
        time -= 5;
        if (time == 0) {
            JLabel label = new JLabel("Wait for more 30 seconds?");
            label.setFont(GUI.getMyFont(15).deriveFont(Font.BOLD));
            if (JOptionPane.showConfirmDialog(new JFrame(), label) == JOptionPane.YES_OPTION) {
                time = 30; WaitFrame.frame.dispose();
            } else {
                System.exit(0);
            }
        }
        return time;
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
