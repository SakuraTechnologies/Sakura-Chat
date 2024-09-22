package sakuratech.chat.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Base64;

public class PostgreSQLController implements PostgreSQL {

    @Override
    public Connection Connector(String url, String user, String pwd) throws SQLException {
        return DriverManager.getConnection(url, user, pwd);
    }

    @Override
    public void Insert(Connection conn, String Name, String Msg, byte[]DecoderKey, byte[]DecoderMsg) {
        try (PreparedStatement pstmt = conn.prepareStatement("INSERT INTO chat(Name, Msg, DecoderKey, DecoderMsg) VALUES (?, ?, ?, ?)")) {
            pstmt.setString(1, Name);
            pstmt.setString(2, Msg);
            pstmt.setString(3, Base64.getEncoder().encodeToString(DecoderKey));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            // 更详细的错误处理
            System.err.println("Failed to insert record: " + e.getMessage());
        }
    }
}
