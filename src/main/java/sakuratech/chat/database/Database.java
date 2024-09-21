package sakuratech.chat.database;

import sakuratech.chat.network.VPNMain;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database implements IDatabase {


    /**
     * 连接到SQLITE3
     * <p>
     * 如果state为0允许连接
     */
    @Override
    public void Connector(String YourDBLink) {
        Connection connection = null;
        try{
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(YourDBLink);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    /**
     * 与SQLITE3
     * <p>
     * 为什么要写这个?
     * 因为要搭配State实现彻底中断写入的一个效果
     */
    @Override
    public void Disconnect() {
    }
}
