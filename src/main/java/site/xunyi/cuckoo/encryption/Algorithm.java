/**
 * 
 */
package site.xunyi.cuckoo.encryption;

/**
 * @author xunyi
 */
public interface Algorithm {
    /**
     * 算法计算
     * @param string
     * @return
     */
    String calc(String... strings);
}
