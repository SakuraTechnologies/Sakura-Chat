package sakuratech.chat;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import sakuratech.chat.crypt.Cryptor;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.SecureRandom;
import java.util.Base64;

import static sakuratech.chat.crypt.Cryptor.Decode;
import static sakuratech.chat.crypt.Cryptor.Encode;


@SpringBootTest
class ChatApplicationTests {

	/*@Test
	void contextLoads() {
	}*/

	/**
	 * 加密测试
	 */

	@Test
	void test() throws Exception {
	Cryptor crypt;
	KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
	keyGenerator.init(256, new SecureRandom());
	SecretKey secretKey = keyGenerator.generateKey();

	byte[] key = secretKey.getEncoded();
	System.out.println("AES 密钥：" + Base64.getEncoder().encodeToString(key));

	String content = "Hello springdoc.cn习近平我草你妈";
	System.out.println("原文：" + content);

	byte[] ret = Encode(key, content.getBytes());
	System.out.println("加密后的密文：" + Base64.getEncoder().encodeToString(ret));

	byte[] raw = Decode(key, ret);
	System.out.println("解密后的原文：" + new String(raw));
	}

	/**
	 * 数据库测试
	 */

	/**@Test
	void connectortest() throws SQLException {
	Database db;
	db = new Database();
	Connection connection1 = db.Connector("D:\\SakanaChar\\chat\\src\\test\\java\\sakuratech\\chat\\SQLite3.db");
		db.Insert(connection1, "hello", "world");


	}*/
	// 不知道为什么不好用
}
