package sakuratech.chat.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class PostgreSQLController implements PostgreSQL {

    @Override
    public Connection Connector(String url, String user, String pwd) throws SQLException {
        return DriverManager.getConnection(url, user, pwd);
    }

    @Override
    public void Insert(Connection Connector, String name, String Msg) {
        try {
            Statement statement = Connector.createStatement();
            String sql = "INSERT INTO chat(name, msg) VALUES ('" + name + "','" + Msg + "')";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
