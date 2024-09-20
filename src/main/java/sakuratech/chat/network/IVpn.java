package sakuratech.chat.network;

public interface IVpn {

    /**
     * 连接到特定频道
     * @param port
     */
    String Connector(int port);

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
     */

    int State();
}
