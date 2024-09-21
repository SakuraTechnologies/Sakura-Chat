package sakuratech.chat.database;

public interface IDatabase {

    /**
     * 连接到SQLITE3
     *
     * 如果state为0允许连接
     */
    void Connector(String YourDBLink);

    /**
     * 与SQLITE3
     *
     * 为什么要写这个?
     * 因为要搭配State实现彻底中断写入的一个效果
     */
    void Disconnect();
}
