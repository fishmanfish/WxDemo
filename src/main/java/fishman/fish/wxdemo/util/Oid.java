package fishman.fish.wxdemo.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class Oid {
  private static Logger log = LoggerFactory.getLogger(Oid.class);

  private static byte[] lock = new byte[0];
  /**
   * 用于建立十六进制字符的输出的小写字符数组
   */
  private static final char[] DIGITS_LOWER = {'0', '1', '2', '3', '4', '5',
     '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

  /**
   * 取得OID。 创建日期：(2001-6-20 20:06:41)
   *
   * @return java.lang.String
   */
  public static String getOid() {
    return getID();
  }

  private static String getID() {
    synchronized (lock) {
      String uuid = java.util.UUID.randomUUID().toString();
      uuid = encryptHmacMD5(uuid);
      return uuid;
    }
  }

  private static String encryptHmacMD5(String strSrc) {
    byte[] bt = strSrc.getBytes();
    byte[] temp = encodeHmacMD5(bt, getHmacKey());
    return new String(encodeHex(temp));
  }

  private static char[] encodeHex(byte[] data) {
    int l = data.length;
    char[] out = new char[l << 1];
    // two characters form the hex value.
    for (int i = 0, j = 0; i < l; i++) {
      out[j++] = DIGITS_LOWER[(0xF0 & data[i]) >>> 4];
      out[j++] = DIGITS_LOWER[0x0F & data[i]];
    }
    return out;
  }

  private static byte[] encodeHmacMD5(byte[] data, byte[] key) {
    Mac mac;

    try {
      mac = Mac.getInstance("HmacMD5");
      mac.init(new SecretKeySpec(key, "HmacMD5"));
    } catch (InvalidKeyException | NoSuchAlgorithmException e) {
      log.error(e.getMessage());
      return new byte[0];
    }

    return mac.doFinal(data);
  }

  private static byte[] getHmacKey() {
    KeyGenerator keyGenerator;

    try {
      keyGenerator = KeyGenerator.getInstance("HmacMD5");
    } catch (NoSuchAlgorithmException var3) {
      throw new RuntimeException(var3.getMessage());
    }

    SecretKey secretKey = keyGenerator.generateKey();
    return secretKey.getEncoded();
  }
}
