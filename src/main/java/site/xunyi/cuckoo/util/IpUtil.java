package site.xunyi.cuckoo.util;

import com.alibaba.fastjson.JSONObject;

/**
 * @author xunyi
 */
public class IpUtil {
    private static final String GET_MY_IP_APIURL_TAOBAO = "http://ip.taobao.com/service/getIpInfo.php?ip=myip";
    private static final String RESULT_DATA = "data";
    private static final String RESULT_DATA_IP = "ip";
    
    public static JSONObject getMyIpInfo() {
        Result<JSONObject> result = HttpUtil.get(GET_MY_IP_APIURL_TAOBAO, null);
        return result.getData();
    }
    
    public static String getMyIp() {
        JSONObject info = getMyIpInfo();
        return info.getJSONObject(RESULT_DATA).getString(RESULT_DATA_IP);
    }
}
