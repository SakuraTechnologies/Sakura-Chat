package sakuratech.chat.database;

import java.sql.Connection;
import java.sql.SQLException;

public interface PostgreSQL {


    Connection Connector(String url, String user, String pwd) throws SQLException;

    void Insert(Connection Connector, String Name, String Msg, byte[]DecoderKey);

}
