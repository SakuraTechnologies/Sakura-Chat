package sakuratech.chat.network;

import sakuratech.chat.crypt.Cryptor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;

import static sakuratech.chat.network.WebSocket.handleAccept;
import static sakuratech.chat.network.WebSocket.handleRead;

public class VPNMain extends Cryptor implements IVpn {
    /**
     * 连接到特定频道
     * 自实现WEBSOCKET服务器
     */
    @Override
    public String Connector(int port) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(port));
        serverSocketChannel.configureBlocking(false);
        Selector selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("WebSocket server started on port " + port);
        while (true) {
            if (selector.select() == 0) continue;
            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectedKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                if (key.isAcceptable()) {
                    int StateCountable = (int) State(0);
                    // 发送允许请求
                    handleAccept(serverSocketChannel, selector);

                } else if (key.isReadable()) {
                    int StateCountable = (int) State(0);
                    // 调用HandleRead
                    handleRead(key);
                    State(0);
                }
                iterator.remove();
            }
        }
    }

    /**
     * 频道掉线
     */
    @Override
    public void Disconnect() {
        // TODO!
        // 好吧或许我们以后不用做这个功能
    }

    /**
     * 异常掉线
     */
    @Override
    public void DisconnectedException() {
        // TODO!
        // 好吧也许这个也是
    }

    /**
     * 状态
     * 0 => connected
     * 1 => failed to connect
     * 2 => exception disconnected
     */
    @Override
    public Object State(int traffic) {
        switch (traffic) {
            case 0:
                return true;
            case 1:
                return null;
            case 2:
                return false;
            default:
                System.err.println("Illegal action for: " + traffic);
                break;
        }
        return traffic;
    }
}
