/**
 * 
 */
package site.xunyi.cuckoo.encryption;

import org.junit.Test;

import cn.hutool.core.lang.Assert;

/**
 * @author xunyi
 */
public class AlgorithmTest {
    Algorithms SHA1 = Algorithms.SHA1;
    Algorithms HmacSHA1 = Algorithms.HmacSHA1;
    
    @Test
    public void testSHA1() {
        Assert.isTrue("35601c3365a361b62b980fda754318c29862d39c".equals(SHA1.calc("get\n/logset\nlogset_id=xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx\nhost=ap-shanghai.cls.myqcloud.com\n")));
    }
    
    @Test
    public void testHmacSHA1() {
        System.out.println(HmacSHA1.calc("1510109254;1510109314", "LUSE4nPK1d4tX5SHyXv6tZXXXXXXXXXX"));
        System.out.println(HmacSHA1.calc("sha1\n1510109254;1510109314\n35601c3365a361b62b980fda754318c29862d39c\n", "a4501294d3a835f8dab6caf5c19837dd19eef357"));
        Assert.isTrue("a4501294d3a835f8dab6caf5c19837dd19eef357".equals(HmacSHA1.calc("1510109254;1510109314", "LUSE4nPK1d4tX5SHyXv6tZXXXXXXXXXX")));
    }
}
