package net.yiliufeng.windows_control.MyUtils;


import android.util.Base64;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

public class RsaUtil {
    public static final String RSA = "RSA";
//    public static final String RSA_PUBLICE = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAvuSqwBA+rvLeQY16f9WI"
//            +"PbCRFSLhCyEnw3zsCGXA0okOVuIY0z9y/0yeAMu1JG+grosTHqGVlGnX5sIXbXE0"
//            +"RDr2JpaNlXuXr8sA+zevAiZXSXyvbWj2awP6eXSI/ikuhU8QZmO58Dh/s5uygic8"
//            +"epdPa/UYo+OK1JwNyOV9MEhDPK3pe02/AXmK7jbbNVR08XSt4N7XwBFBJkmCbMBX"
//            +"G9mv3WVPVrDf/cgg3rAjQWKzs2pWYiz/oYc/KhPLGRjUaVaaxbE0AIDdvuJnDAe6"
//            +"wEbPxj9AaiYaersnDnx3SEno/EK5h7lTNeaiz56rsFW5MOx9olgSirQCQIYahWw8"
//            +"JQIDAQAB";                    MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAvuSqwBA+rvLeQY16f9WI"
    public static final String RSA_PUBLICE = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA7jSgfUnYyfsqUCOTIMzp" +
        "W1lsL05D/alxb79oe8CbpDozs3NmfRcjhk+hcyJ9juIxSm+QgEl7M7jNjFAdjCSU" +
        "wAY7vsnf9fRl4QTfw/YQwYkPFGoWqKZyKQO2wdN8J142sF75i0cf77S4VKC50U2M" +
        "zhf0P6YlZViiNnHrIlSkTIw71lpJMlDch87wcoTrF30eDUK9mthHY9GMok7h19BF" +
        "m4iapWtlZ2Q0Cgf0KvSLLbmOD+fs5DnSkVTX8/O6SP8zrat+ak6EXFTbqOpTQOw7" +
        "zOekm6jmGr7I5KGwInj/Rkw8OA0uNwm5vHSGm66DO4U055bcZNFw/rwJ10A/poGv" +
        "mQIDAQAB";
    /**
     * 加密方式，android的
     */
//    public static final String TRANSFORMATION = "RSA/ECB/PKCS1Padding";
    public static final String TRANSFORMATION = "RSA/None/NoPadding";
    public static final String ECB_PKCS1_PADDING = "RSA/ECB/PKCS1Padding";
    /**加密方式，标准jdk的*/
//    public static final String TRANSFORMATION = "RSA/None/PKCS1Padding";

    /** *//**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;

    /** *//**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 128;

    /*
    public static final int DEFAULT_KEY_SIZE = 2048;//秘钥默认长度
    public static final byte[] DEFAULT_SPLIT = "#PART#".getBytes();    // 当要加密的内容超过bufferSize，则采用partSplit进行分块加密
    public static final int DEFAULT_BUFFERSIZE = (DEFAULT_KEY_SIZE / 8) - 11;// 当前秘钥支持加密的最大字节数
    * */

    /**
     * 随机生成RSA密钥对
     *
     * @param keyLength 密钥长度，范围：512～2048
     *                  一般1024
     * @return
     */
    public static KeyPair generateRSAKeyPair(int keyLength) {
        try {
            KeyPairGenerator kpg = KeyPairGenerator.getInstance(RSA);
            kpg.initialize(keyLength);
            return kpg.genKeyPair();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 得到公钥
     *
     * @param algorithm
     * @param bysKey
     * @return
     */
    private static PublicKey getPublicKeyFromX509(String algorithm,
                                                  String bysKey) throws Exception {
        byte[] decodedKey = Base64.decode(bysKey, Base64.DEFAULT);
        X509EncodedKeySpec x509 = new X509EncodedKeySpec(decodedKey);

        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        return keyFactory.generatePublic(x509);
    }

    /**
     * 使用公钥加密
     *
     * @param content 要加密的字符串
     * @return
     */
    public static String encryptByPublic(String content) {
        try {
            PublicKey pubkey = getPublicKeyFromX509(RSA, RSA_PUBLICE);
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, pubkey);
            byte plaintext[] = content.getBytes("UTF-8");
            byte[] output = cipher.doFinal(plaintext);
            String s = Base64.encodeToString(output,Base64.NO_WRAP);
            return s;

        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }



}