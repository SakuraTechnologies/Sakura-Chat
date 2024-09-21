package sakuratech.chat.network;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public interface IVpn {

    /**
     * 连接到特定频道
     */
    String Connector(int port) throws Exception;

    /**
     * 状态
     * 0 => connected
     * 1 => failed to connect
     * 2 => exception disconnected
     */

    Object State(int traffic);
}
