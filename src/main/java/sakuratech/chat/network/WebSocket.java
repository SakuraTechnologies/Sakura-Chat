package sakuratech.chat.network;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class WebSocket {

    public static void handleAccept(ServerSocketChannel serverSocketChannel, Selector selector) throws IOException {
        SocketChannel socketChannel = serverSocketChannel.accept();
        socketChannel.configureBlocking(false);
        socketChannel.register(selector, SelectionKey.OP_READ);

        System.out.println("New client connected: " + socketChannel.getRemoteAddress());

        // Send handshake response
        sendHandshakeResponse(socketChannel);
    }

    public static String handleRead(SelectionKey key) throws IOException {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        int read = socketChannel.read(buffer);
        if (read == -1) {
            socketChannel.close();
        }

        buffer.flip();
        byte[] data = new byte[buffer.remaining()];
        buffer.get(data);

        String message = new String(data);
        System.out.println("Received message from client: " + message);

        // Send response back to the client
        sendResponse(socketChannel, "Message received: " + message);
        return message;
    }

    private static void sendHandshakeResponse(SocketChannel socketChannel) throws IOException {
        String response = "HTTP/1.1 101 Switching Protocols\r\n" +
                "Upgrade: websocket\r\n" +
                "Connection: Upgrade\r\n" +
                "Sec-WebSocket-Accept: s3pPLMBiTxaQ9kYGzzhZRbK+xOo=\r\n" +
                "\r\n";
        socketChannel.write(ByteBuffer.wrap(response.getBytes()));
    }

    private static void sendResponse(SocketChannel socketChannel, String message) throws IOException {
        byte[] bytes = message.getBytes();
        ByteBuffer buffer = ByteBuffer.allocate(bytes.length);
        buffer.put(bytes);
        buffer.flip();
        socketChannel.write(buffer);
    }
}
