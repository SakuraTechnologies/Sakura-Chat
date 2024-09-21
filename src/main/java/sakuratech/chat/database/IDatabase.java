package sakuratech.chat.database;

public interface IDatabase {

    /**
     * 连接到SQLITE3
     * <p>
     * 如果state为0允许连接
     */
    void Connector(String YourDBLink);
}
