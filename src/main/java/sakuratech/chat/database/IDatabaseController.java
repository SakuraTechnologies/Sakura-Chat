package sakuratech.chat.database;

import java.sql.Connection;
import java.sql.SQLException;

public interface IDatabaseController {

    void Insert(Connection connection, String name, String Msg) throws SQLException;

    void Delete(Connection connection);

}
