/**
 * 
 */
package site.xunyi.cuckoo.kafka;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.springframework.beans.factory.annotation.Value;

import site.xunyi.cuckoo.data.Message;

/**
 * @author xunyi
 */
public class KafkaConsumerImpl implements KafkaConsumer<Message>{
    private final Properties prop = new Properties();
    private static final Duration DURATION = Duration.ofSeconds(1);
    
    /**
     * kafka的ip/端口号
     */
    private static String bootstrapServers;
    /**
     * key的反序列化方式
     */
    private static String keyDeserializer;
    /**
     * value的反序列化方式
     */
    private static String valueDeserializer;
    /**
     * 自动重置偏移量的策略
     */
    private static String autoOffsetReset;
    
    @Value(value = "${spring.kafka.bootstrap-servers}")
    public void setBootstrapServers(String bootstrapServers) {
        KafkaConsumerImpl.bootstrapServers = bootstrapServers;
    }

    @Value(value = "${spring.kafka.consumer.key-deserializer}")
    public void setKeyDeserializer(String keyDeserializer) {
        KafkaConsumerImpl.keyDeserializer = keyDeserializer;
    }

    @Value(value = "${spring.kafka.consumer.value-deserializer}")
    public void setValueDeserializer(String valueDeserializer) {
        KafkaConsumerImpl.valueDeserializer = valueDeserializer;
    }

    @Value(value = "${spring.kafka.consumer.auto-offset-reset}")
    public void setAutoOffsetReset(String autoOffsetReset) {
        KafkaConsumerImpl.autoOffsetReset = autoOffsetReset;
    }

    private String account;
    public String getAccount() {
        return this.account;
    }
    
    org.apache.kafka.clients.consumer.KafkaConsumer<String, Message> c;
    
    public KafkaConsumerImpl(String account) {
        this.account = account;
        
        prop.put("bootstrap.servers", bootstrapServers);
        prop.put("key.deserializer", keyDeserializer);
        prop.put("value.deserializer", valueDeserializer);
        prop.put("auto.offset.reset", autoOffsetReset);
        prop.put("group.id",account);
//        prop.put("enable.auto.commit", "true"); //默认为true自动提交
//        prop.put("auto.commit.interval.ms", "500"); //设置默认自动提交时间 ，默认值为5000ms
        c = new org.apache.kafka.clients.consumer.KafkaConsumer<String, Message>(prop);
    }
    
    @Override
    public List<Message> receive(Collection<String> topics) {
        c.subscribe(topics);
        ConsumerRecords<String, Message> records = c.poll(DURATION);
        
        List<Message> res = new ArrayList<>(records.count());
        if(!records.isEmpty()) {
            records.forEach(record -> {
                res.add(record.value());
            });
        }
        return res;
    }
}
