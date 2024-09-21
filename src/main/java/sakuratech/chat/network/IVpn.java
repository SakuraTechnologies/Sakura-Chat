package sakuratech.chat.network;

import java.io.IOException;
import java.sql.SQLException;

public interface IVpn {

    /**
     * 连接到特定频道
     */
    String Connector(int port) throws IOException, SQLException;

    /**
     * 状态
     * 0 => connected
     * 1 => failed to connect
     * 2 => exception disconnected
     */

    Object State(int traffic);
}
