/**
 * 
 */
package site.xunyi.cuckoo.kafka;

import java.util.Arrays;
import java.util.Collections;
import java.util.Properties;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.DeleteTopicsResult;
import org.apache.kafka.clients.admin.KafkaAdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author xunyi
 */
public class KafkaTopicTool {
    private AdminClient client;
    
    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String server;
    
    @Value(value = "${spring.kafka.num.partitions}")
    private int defaultPartitions;
    
    @Value(value = "${spring.kafka.default.replication.factor}")
    private short defaultReplication;

    private Properties props = new Properties();
    {
        props.put("bootstrap.servers", server);
        
        client = KafkaAdminClient.create(props);
    }
    
    public CreateTopicsResult createTopic(String topic) {
        return client.createTopics(Collections.singleton(new NewTopic(topic, defaultPartitions, defaultReplication)));
    }
    
    public DeleteTopicsResult deleteTopic(String... topics) {
        return client.deleteTopics(Arrays.asList(topics));
    }
}
