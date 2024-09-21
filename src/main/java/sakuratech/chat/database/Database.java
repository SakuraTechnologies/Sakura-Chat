package sakuratech.chat.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Database implements IDatabase, IDatabaseController {

    /**
     * 连接到SQLITE3
     * <p>
     * 如果state为0允许连接
     */
    @Override
    public void Connector(String YourDBLink) {
        Connection connection = null;
        try {
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

    @Override
    public void Insert(Connection connection, String name, String Msg) throws SQLException {
        Connection connector;
        connector = (Connection) connection.createStatement();
        PreparedStatement statement = connector.prepareStatement(
                "insert into chat(chat) values(? ? ?)"
        );
        statement.setString(1, name);
        statement.setString(2, Msg);
        statement.executeUpdate();
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
        String Date = simpleDateFormat.format(date);
        statement.setString(3, Date);
    }

    @Override
    public void Delete(Connection connection) {
        // TODO!
    }
}
