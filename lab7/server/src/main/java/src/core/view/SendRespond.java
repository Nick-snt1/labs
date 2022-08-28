package src.core.view;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.net.InetSocketAddress;
import java.nio.channels.DatagramChannel;

import java.util.concurrent.TimeUnit;

import java.util.Arrays;

import java.io.*;

public class SendRespond {
    private final DatagramChannel channel;

    public SendRespond(DatagramChannel channel) { this.channel = channel; }

    public void send(Serializable sendingData, InetSocketAddress addr) {
        try {
            DatagramSocket socket = channel.socket();

            byte[] send = objectToBytes(sendingData);

            byte[] size = objectToBytes(send.length);

            socket.send(
                new DatagramPacket(
                    size, size.length, addr.getAddress(), addr.getPort() ));

            bigDataSend(socket, send, addr);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void bigDataSend(DatagramSocket socket, byte[] data, InetSocketAddress addr) throws IOException {
        int packets = data.length/64000 + (data.length%64000 == 0 ? 0 : 1);
        int from = 0, to = 64000;
        for (int i = 0; i < packets; i++) {
            if (i == packets - 1) to = data.length;
            byte[] send = Arrays.copyOfRange(data, from, to);

            socket.send(
                new DatagramPacket(
                    send, send.length, addr.getAddress(), addr.getPort() ));

            try { TimeUnit.MILLISECONDS.sleep(10); } catch (Exception e) { }//was 30
            from += 64000; to += 64000;
        }
    }

    private byte[] objectToBytes(Serializable object) throws IOException {
        ByteArrayOutputStream bs = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(bs)) {
            oos.writeObject(object); return bs.toByteArray();
        }
    }
}

/**
private void DTOSend(DTO dto, InetSocketAddress clientAddr) throws IOException {
    String data = dto.getGeneral();
    int ammountOfPackets = data.length/64000 + (data.length%64000 == 0 ? 0 : 1);
}

serverSocket.send(
    new DatagramPacket(
        send, send.length, clientAddr.getAddress(), clientAddr.getPort() ));
*/
