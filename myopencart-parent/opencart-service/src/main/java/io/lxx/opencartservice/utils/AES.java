package io.lxx.opencartservice.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;

public class AES {

    private static SecretKeySpec secretKey;
    private static byte[] key;

    //自定义构造方法 设置秘钥
    public static void setKey(String mykey) throws Exception {
        MessageDigest sha = null;
        key = mykey.getBytes("UTF-8");
        sha = MessageDigest.getInstance("SHA-1");
        key = sha.digest(key);
        key = Arrays.copyOf(key,16);
        secretKey = new SecretKeySpec(key, "AES");
    }
    //加密方法
    public static String encrypt(String strToEncrypt,String secret) throws Exception {
        setKey(secret);
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");//AES/ECB/PKCS5Padding加密算法
        cipher.init(Cipher.ENCRYPT_MODE,secretKey);
        return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
    }
    //解密方法
    public static String decrypt(String strToDecrypt,String secret) throws Exception {
        setKey(secret);
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");//AES/ECB/PKCS5Padding加密算法
        cipher.init(Cipher.ENCRYPT_MODE,secretKey);
        return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
    }
}
