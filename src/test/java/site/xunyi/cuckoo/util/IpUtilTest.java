/**
 * 
 */
package site.xunyi.cuckoo.util;

import org.junit.Test;

import com.alibaba.fastjson.JSONObject;

import site.xunyi.cuckoo.utils.IpUtil;

/**
 * @author xunyi
 */
public class IpUtilTest {
    @Test
    public void testGetMyIpInfo() {
        JSONObject myIpInfo = IpUtil.getMyIpInfo();
        System.out.println(myIpInfo);
    }
    
    @Test
    public void testGetMyIp() {
        String myIp = IpUtil.getMyIp();
        System.out.println(myIp);
    }
}
