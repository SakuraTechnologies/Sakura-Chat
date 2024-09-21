package sakuratech.chat.network;

import sakuratech.chat.crypt.Cryptor;
import sakuratech.chat.database.PostgreSQLController;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Set;

import static sakuratech.chat.network.WebSocket.handleAccept;
import static sakuratech.chat.network.WebSocket.handleRead;

public class VPNMain extends Cryptor implements IVpn, DBInfo{

    /**
     * 连接到特定频道
     * 自实现WEBSOCKET服务器
     */
    @Override
    // webSocket
    // 用于和前端进行频道交互
    public String Connector(int port) throws IOException, SQLException {
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
                    // 发送允许请求
                    handleAccept(serverSocketChannel, selector);
                    State(0);
                } else if (key.isReadable()) {
                    String msg = handleRead(key);
                    State(0);
                    PostgreSQLController db = new PostgreSQLController();
                    Connection connect = db.Connector(DB_URL, DB_USER, DB_PASSWORD);
                    db.Insert(connect, "test", msg);
                }
                iterator.remove();
            }
        }
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
