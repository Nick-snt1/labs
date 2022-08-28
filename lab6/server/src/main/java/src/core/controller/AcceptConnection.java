package src.core.controller;

import java.nio.channels.DatagramChannel;
import java.nio.ByteBuffer;

import java.io.ObjectInputStream;
import java.io.ByteArrayInputStream;
import java.io.*;

import java.util.logging.*;

import java.net.*;

public class AcceptConnection {

    private DatagramChannel channel;

    private ByteBuffer buffer;

    private static final Logger LOGGER = Logger.getLogger(AcceptConnection.class.getName());

    public AcceptConnection(DatagramChannel channel, ByteBuffer buffer) {
        LOGGER.info("New AcceptConnection created");
        this.channel = channel;
        this.buffer = buffer;
    }

    public SocketAddress receiveData() throws IOException {
        LOGGER.info("Method receiveData executed");
        buffer.clear();
        return establishConnection();
    }

    private SocketAddress establishConnection() throws IOException {
        SocketAddress clientAddr;
        while ((clientAddr = channel.receive(buffer)) == null) { }
        LOGGER.info("Data received");
        channel.send(ByteBuffer.allocate(1), (InetSocketAddress) clientAddr);
        LOGGER.info("Empty respond sent");
        LOGGER.info("Method establishConnection executed");
        return clientAddr;
    }

    public Object getObject() throws IOException, ClassNotFoundException  {
        LOGGER.info("Object taken out from inner buffer");
        return new ObjectInputStream(new ByteArrayInputStream(buffer.array())).readObject();
    }

}
