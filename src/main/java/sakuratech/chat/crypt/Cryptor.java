package sakuratech.chat.crypt;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public abstract class Cryptor {

    /**
     * 加密
     * @param key		密钥
     * @param data		加密数据
     * @return			密文
     * @throws Exception
     */
    public static byte[] encode(byte[] key, byte[] data) throws Exception {
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        return cipher.doFinal(data);
    }

    /**
     * 解密
     * @param key		密钥
     * @param data		密文
     * @return			解密后的数据
     * @throws Exception
     */
    public static byte[] decode(byte[] key, byte[] data) throws Exception {
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        return cipher.doFinal(data);
    }
}
