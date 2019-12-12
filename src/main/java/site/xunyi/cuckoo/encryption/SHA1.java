/**
 * 
 */
package site.xunyi.cuckoo.encryption;

import java.security.MessageDigest;

/**
 * @author xunyi
 */
public class SHA1 implements Algorithm{
    /**
     * 包内可访问，是的Algorithms的单例模式不会轻易被破坏
     */
    SHA1(){};
    
    /**
     * SHA1加密
     */
    @Override
    public String calc(String... strings) {
        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f' };
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
            mdTemp.update(strings[0].getBytes("UTF-8"));
            byte[] md = mdTemp.digest();
            int j = md.length;
            char buf[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
                buf[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(buf);
        } catch (Exception e) {
            return null;
        }
    }
}
