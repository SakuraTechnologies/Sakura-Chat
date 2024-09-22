package sakuratech.chat.network;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class WebSocket {

    private static final ChannelManager channelManager = new ChannelManager();

    public static void HandleAccept(ServerSocketChannel serverSocketChannel, Selector selector) throws IOException {
        SocketChannel socketChannel = serverSocketChannel.accept();
        socketChannel.configureBlocking(false);
        socketChannel.register(selector, SelectionKey.OP_READ);

        System.out.println("New client connected: " + socketChannel.getRemoteAddress());

        // Send handshake response
        SendHandshakeResponse(socketChannel);

        // Generate a unique channel ID
        String channelId = generateChannelId();
        while (!channelManager.addChannel(channelId, socketChannel.keyFor(selector))) {
            // 如果频道ID已存在，重新生成
            channelId = generateChannelId();
        }
    }

    public static String HandleRead(SelectionKey key) throws IOException {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        int read = socketChannel.read(buffer);
        if (read == -1) {
            socketChannel.close();
            return "";
        }

        buffer.flip();
        byte[] data = new byte[buffer.remaining()];
        buffer.get(data);

        String message = new String(data);
        System.out.println("Received message from client: " + message);

        // Extract username and message content
        String[] result = ExtractUsernameAndContent(message);
        if (result == null || result.length != 2) {
            System.out.println("Invalid message format: " + message);
            return "";
        }
        String username = result[0];
        String content = result[1];

        // Send response back to the client with username
        String response = "Message received from " + username + ": " + content;
        SendResponse(socketChannel, response);

        return message;
    }

    public static void QuerySelectionKey(String channelId) {
        SelectionKey key = channelManager.getKeyForChannel(channelId);
        if (key != null) {
            System.out.println("Found SelectionKey for channel ID: " + channelId);
            // 可以在这里做进一步的操作，如发送消息等
        } else {
            System.out.println("No SelectionKey found for channel ID: " + channelId);
        }
    }

    public static void RemoveSelectionKey(String channelId) {
        SelectionKey key = channelManager.getKeyForChannel(channelId);
        if (key != null) {
            key.cancel(); // 取消 SelectionKey 的注册
            channelManager.removeChannel(channelId);
            System.out.println("SelectionKey removed for channel ID: " + channelId);
        } else {
            System.out.println("No SelectionKey found for channel ID: " + channelId);
        }
    }

    static String[] ExtractUsernameAndContent(String message) {
        String[] parts = message.split(": ");
        if (parts.length != 2) {
            return null;
        }
        return parts;
    }

    private static void SendHandshakeResponse(SocketChannel socketChannel) throws IOException {
        String response = "HTTP/1.1 101 Switching Protocols\r\n" +
                "Upgrade: websocket\r\n" +
                "Connection: Upgrade\r\n" +
                "Sec-WebSocket-Accept: s3pPLMBiTxaQ9kYGzzhZRbK+xOo=\r\n" +
                "\r\n";
        socketChannel.write(ByteBuffer.wrap(response.getBytes()));
    }

    private static void SendResponse(SocketChannel socketChannel, String message) throws IOException {
        byte[] bytes = message.getBytes();
        ByteBuffer buffer = ByteBuffer.allocate(bytes.length);
        buffer.put(bytes);
        buffer.flip();
        socketChannel.write(buffer);
    }

    // 生成一个基于时间戳和随机数的频道ID
    private static String generateChannelId() {
        long timestamp = System.currentTimeMillis();
        int randomNum = (int) (Math.random() * Integer.MAX_VALUE);
        return "SAKURACHATTING" + timestamp + "-" + randomNum;
    }
}
