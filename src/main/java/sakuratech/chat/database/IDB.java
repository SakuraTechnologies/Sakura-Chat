package sakuratech.chat.database;

public interface IDB {

    /**
     * 链接到服务器，数据库准备采用Redis
     */

    void Connector();

    /**
     * 向Redis服务器插入数据
     */
}
