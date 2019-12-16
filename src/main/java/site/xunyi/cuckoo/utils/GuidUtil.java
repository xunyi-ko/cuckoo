/**
 * 
 */
package site.xunyi.cuckoo.utils;

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
