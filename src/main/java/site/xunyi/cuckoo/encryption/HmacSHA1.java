package site.xunyi.cuckoo.encryption;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;


/**
 * HmacSHA1加密
 * @author xunyi
 */
public class HmacSHA1 implements Algorithm {
    private static final String MAC_NAME = "HmacSHA1"; 

    public String calc(String... strings) {
        try {
            SecretKeySpec signingKey = new SecretKeySpec(strings[1].getBytes(), MAC_NAME);
            Mac mac = Mac.getInstance(MAC_NAME);
            mac.init(signingKey);
            return byte2hex(mac.doFinal(strings[0].getBytes()));
        } catch (NoSuchAlgorithmException e) {
             e.printStackTrace();
        } catch (InvalidKeyException e) {
             e.printStackTrace();
        }
       return null;
    }
    
    private String byte2hex(byte[] b) {
        StringBuilder hs = new StringBuilder();
        String stmp;
        for (int n = 0; b!=null && n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0XFF);
            if (stmp.length() == 1)
                hs.append('0');
            hs.append(stmp);
        }
        return hs.toString();
    }
}
