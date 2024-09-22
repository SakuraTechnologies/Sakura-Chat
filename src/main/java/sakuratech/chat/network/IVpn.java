package sakuratech.chat.network;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import static sakuratech.chat.network.VPNMain.SetPortVal;

public interface IVpn {

    static int Port = SetPortVal();

    /**
     * 连接到特定频道
     */
    String CreateChattingRoomWS() throws Exception;

    /**
     * 状态
     * 0 => connected
     * 1 => failed to connect
     * 2 => exception disconnected
     */

    Object State(int traffic);
}
