package site.xunyi.cuckoo.scheduler;

import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.apache.logging.log4j.util.Strings;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import site.xunyi.cuckoo.encryption.Algorithms;
import site.xunyi.cuckoo.util.HttpUtil;
import site.xunyi.cuckoo.util.IpUtil;

/**
 * @author xunyi
 */
@Component
public class AutoParsingIp {
    private String myIp;
    private Algorithms SHA1 = Algorithms.SHA1;
    private Algorithms HmacSHA1 = Algorithms.HmacSHA1;
    private final String url = "";
    
    @Scheduled(fixedDelay = 60_000)
    public void autoChangeParsing() {
        getMyIp();
        
        String ip = IpUtil.getMyIp();
        if(myIp.equals(ip)) {
            return;
        }
        
        // TODO 重新解析
        // 添加参数
        TreeMap<String, Object> map = new TreeMap<>((e1, e2) -> {
            return e1.compareTo(e2);
        });
        map.put("", "");
        
        // 获取签名
        String signature = getSignature(map);
        map.put("signature", signature);
        
        
        myIp = ip;
    }
    
    private void getMyIp() {
        if(Strings.isNotBlank(myIp)) {
            return;
        }
        
        myIp = "";
    }
 
    // TODO
    private String getSignature(Map<String, Object> params) {
        StringBuilder sb = new StringBuilder();
        for(Entry<String, Object> e : params.entrySet()) {
            String key = e.getKey();
            String value = e.getValue().toString();
            
            sb.append("&").append(key).append("=").append(value).append("\n");
        }
        
        String string = SHA1.calc("GET" + sb.toString().substring(1));
        HmacSHA1.calc(string, params.get("key").toString());
        
        return "GET" + sb.toString().substring(1);
    }
}
