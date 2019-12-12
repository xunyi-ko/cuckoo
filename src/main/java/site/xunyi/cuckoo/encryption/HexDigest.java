/**
 * 
 */
package site.xunyi.cuckoo.encryption;

import java.security.MessageDigest;

/**
 * 转换为16进制字符串
 * @author xunyi
 */
public class HexDigest implements Algorithm{
    public String calc(String... strings) {
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        byte[] md5Bytes = md5.digest(strings[0].getBytes());
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16)
                hexValue.append("0");
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }
}
