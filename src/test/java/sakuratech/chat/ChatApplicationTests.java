package sakuratech.chat;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import sakuratech.chat.crypt.Cryptor;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;


import static sakuratech.chat.crypt.Cryptor.decode;
import static sakuratech.chat.crypt.Cryptor.encode;

@SpringBootTest
class ChatApplicationTests {

	@Test
	void contextLoads() {
	}

	/*@Test
	void test() throws Exception {
		Cryptor crypt;
		KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
		keyGenerator.init(256, new SecureRandom());
		SecretKey secretKey = keyGenerator.generateKey();

		byte[] key = secretKey.getEncoded();
		System.out.println("AES 密钥：" + Base64.getEncoder().encodeToString(key));

		String content = "Hello springdoc.cn习近平我草你妈";
		System.out.println("原文：" + content);

		byte[] ret = encode(key, content.getBytes());
		System.out.println("加密后的密文：" + Base64.getEncoder().encodeToString(ret));

		byte[] raw = decode(key, ret);
		System.out.println("解密后的原文：" + new String(raw));
	}*/
}
