package sakuratech.chat;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ChatApplicationTests {

	/*@Test
	void contextLoads() {
	}*/

	/**
	 * 加密测试
	 */

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
