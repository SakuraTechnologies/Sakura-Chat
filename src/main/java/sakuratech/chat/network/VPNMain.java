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
import java.util.Base64;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;
import java.util.stream.IntStream;

import static sakuratech.chat.network.WebSocket.*;

public class VPNMain extends Cryptor implements IVpn, DBInfo {

    /**
     * 创建特定频道所使用的WEBSOCKET服务端
     * 可以使前端连接
     * 自实现WEBSOCKET服务器
     */
    @Override
    public String CreateChattingRoomWS() throws Exception {
        // 启动WS
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        // 绑定随机的端口
        serverSocketChannel.socket().bind(new InetSocketAddress(Port));
        serverSocketChannel.configureBlocking(false);
        // 创建选择器
        Selector selector = Selector.open();
        // 注册选择器，将SeletionKey注册为OP_ACCEPT
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("WebSocket server started on port " + Port);
        //  创建一个循环
        while (true) {
            // 检测选择器的值
            if (selector.select() == 0) continue;
            Set<SelectionKey> SelectedKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = SelectedKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey Key = iterator.next();
                if (Key.isAcceptable()) {
                    HandleAccept(serverSocketChannel, selector);
                    State(0);
                } else if (Key.isReadable()) {
                    // 读取数据
                    String Msg = HandleRead(Key);
                    String[] Contents = ExtractUsernameAndContent(Msg);
                    // 使用'断言'来判断用户名称和内容的值
                    assert Contents != null;
                    // 从数组中读取数据
                    String UsrName = Contents[0];
                    String MsgContent = Contents[1];
                    // 创建密钥
                    Cryptor Cryptor;
                    /**
                     * 选用AES加密方法，支持中文，实例化密钥
                     * KEYSIZE: 256
                     * 然后在key和ret中存储密钥和加密后的数据
                     * 使用DecodeData存储解密数据
                     * 连接数据库进行插入操作
                     */
                    KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
                    keyGenerator.init(256, new SecureRandom());
                    SecretKey secretKey = keyGenerator.generateKey();
                    byte[] Keys = secretKey.getEncoded();
                    byte[] Ret = Encode(Keys, MsgContent.getBytes());
                    byte[] DecodeData = Decode(Keys, Ret);
                    // 设定特定的State
                    State(0);
                    PostgreSQLController db = new PostgreSQLController();
                    Connection Connect = db.Connector(DB_URL, DB_USER, DB_PASSWORD);
                    /**
                     * Connect => 连接（传入这个值是为了后续的数据库操作）
                     * UsrName => 获取用户名
                     * Ret => 获取加密后的数据
                     * Keys => 获取密钥
                     * DecodeData => 获取解密后的数据
                     */
                    db.Insert(
                            Connect,
                            UsrName,
                            Base64.getEncoder().encodeToString(Ret),
                            Keys,
                            Base64.getDecoder().decode(DecodeData));
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

    /**
     * 生成一个包含指定数量伪随机端口号的流。
     *
     * @param streamSize 生成的值的数量
     * @return 一个包含伪随机端口号的流
     * @throws IllegalArgumentException 如果 streamSize 小于零
     */
    public static IntStream GeneratePort(long streamSize) {
        if (streamSize < 0) {
            throw new IllegalArgumentException("streamSize must be non-negative");
        }

        Random random = new Random();
        return IntStream.range(0, (int) streamSize).map(i -> random.nextInt(65536));
    }

    /**
     * 获取一个随机端口号。
     *
     * @return 随机端口号
     */
    public static int GetRandomPort() {
        return GeneratePort(1).findFirst().orElse(0);
    }

    public static int SetPortVal() {
        IntStream ports = GeneratePort(10);
        ports.forEach(System.out::println);
        int port = GetRandomPort();
        return port;
    }
}