/**
 * 
 */
package site.xunyi.cuckoo.kafka;

import java.util.Collection;
import java.util.List;

/**
 * @author xunyi
 */
public interface KafkaConsumer<T> {
    List<T> receive(Collection<String> topics);
}
