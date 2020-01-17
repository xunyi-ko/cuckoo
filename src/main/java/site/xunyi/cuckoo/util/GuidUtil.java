/**
 * 
 */
package site.xunyi.cuckoo.util;

/**
 * @author xunyi
 */
public class GuidUtil {
    public static String initGuid(EntityType entityType) {
        return entityType.name() + System.currentTimeMillis();
    }
    
    public enum EntityType{
        ROOM;
    }
}
