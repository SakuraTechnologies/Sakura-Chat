package sakuratech.chat.network;

import sakuratech.chat.crypt.Cryptor;
import sakuratech.chat.database.PostgreSQLController;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.security.SecureRandom;
import java.sql.Connection;
import java.util.Arrays;
import java.util.Base64;
import java.util.Iterator;
import java.util.Set;

import static sakuratech.chat.network.WebSocket.*;

public class VPNMain extends Cryptor implements IVpn, DBInfo{

    /**
     * 连接到特定频道
     * 自实现WEBSOCKET服务器
     */
    @Override
    public String Connector(int port) throws Exception {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(port));
        serverSocketChannel.configureBlocking(false);
        Selector selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("WebSocket server started on port " + port);
        while (true) {
            if (selector.select() == 0) continue;
            Set<SelectionKey> SelectedKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = SelectedKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey Key = iterator.next();
                if (Key.isAcceptable()) {
                    HandleAccept(serverSocketChannel, selector);
                    State(0);
                } else if (Key.isReadable()) {
                    String Msg = HandleRead(Key);
                    String[] usrname = ExtractUsernameAndContent(Msg);
                    assert usrname != null;
                    String UsrName = usrname[0];
                    String MsgContent = usrname[1];
                    Cryptor Cryptor;
                    KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
                    keyGenerator.init(256, new SecureRandom());
                    SecretKey secretKey = keyGenerator.generateKey();
                    byte[] Keys = secretKey.getEncoded();
                    byte[] ret = Encode(Keys, MsgContent.getBytes());
                    State(0);
                    PostgreSQLController db = new PostgreSQLController();
                    Connection Connect = db.Connector(DB_URL, DB_USER, DB_PASSWORD);
                    db.Insert(Connect, UsrName, Base64.getEncoder().encodeToString(ret), Keys);
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
