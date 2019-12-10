package site.xunyi.cuckoo.scheduler;

import org.apache.logging.log4j.util.Strings;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import site.xunyi.cuckoo.utils.IpUtil;

/**
 * @author xunyi
 */
@Component
public class AutoParsingIp {
    private String myIp;
    
    @Scheduled(fixedDelay = 60_000)
    public void autoChangeParsing() {
        getMyIp();
        
        String ip = IpUtil.getMyIp();
        if(myIp.equals(ip)) {
            return;
        }
        
        // TODO 重新解析
        
        
        myIp = ip;
    }
    
    private void getMyIp() {
        if(Strings.isNotBlank(myIp)) {
            return;
        }
        
        myIp = "";
    }
    
}
