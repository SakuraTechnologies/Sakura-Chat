package sakuratech.chat.network;

import java.io.IOException;

public interface IVpn {

    /**
     * 连接到特定频道
     */
    String Connector(int port) throws IOException;

    /**
     * 频道掉线
     */

    void Disconnect();

    /**
     * 异常掉线
     */

    void DisconnectedException();

    /**
     * 状态
     * 0 => connected
     * 1 => failed to connect
     * 2 => exception disconnected
     */

    Object State(int traffic);
}
