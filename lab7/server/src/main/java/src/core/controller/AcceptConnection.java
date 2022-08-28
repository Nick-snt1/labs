package src.core.controller;

import java.nio.channels.DatagramChannel;
import java.nio.ByteBuffer;

import java.io.ObjectInputStream;
import java.io.ByteArrayInputStream;
import java.io.*;

import java.net.*;

public class AcceptConnection {

    private DatagramChannel channel;

    private ByteBuffer buffer;

    public AcceptConnection(DatagramChannel channel, ByteBuffer buffer) {
        this.channel = channel;
        this.buffer = buffer;
    }

    public SocketAddress receiveData() throws IOException {
        //channel.configureBlocking(false);
        buffer.clear();
        SocketAddress clientAddr;
        while ((clientAddr = channel.receive(buffer)) == null) { }
        channel.send(ByteBuffer.allocate(1), (InetSocketAddress) clientAddr);
        return clientAddr;
    }

    public Object getObject() throws Exception {
        return new ObjectInputStream(new ByteArrayInputStream(buffer.array())).readObject();
    }

}
