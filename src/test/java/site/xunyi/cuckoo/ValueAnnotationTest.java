/**
 * 
 */
package site.xunyi.cuckoo;

import java.lang.reflect.Field;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author xunyi
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class ValueAnnotationTest {
    private static String bootstrapServers;
    
    private static String keyDeserializer;
    
    private static String valueDeserializer;
    
    private static String autoOffsetReset;
    
    @Test
    public void testValueAnnotation() throws IllegalArgumentException, IllegalAccessException {
        Field[] fields = this.getClass().getDeclaredFields();
        
        for(Field field : fields) {
            field.setAccessible(true);
            
            System.out.println(field.get(this));
        }
    }

    @Value(value = "${spring.kafka.bootstrap-servers}")
    public void setBootstrapServers(String bootstrapServers) {
        ValueAnnotationTest.bootstrapServers = bootstrapServers;
    }

    @Value(value = "${spring.kafka.consumer.key-deserializer}")
    public void setKeyDeserializer(String keyDeserializer) {
        ValueAnnotationTest.keyDeserializer = keyDeserializer;
    }

    @Value(value = "${spring.kafka.consumer.value-deserializer}")
    public void setValueDeserializer(String valueDeserializer) {
        ValueAnnotationTest.valueDeserializer = valueDeserializer;
    }

    @Value(value = "${spring.kafka.consumer.auto-offset-reset}")
    public void setAutoOffsetReset(String autoOffsetReset) {
        ValueAnnotationTest.autoOffsetReset = autoOffsetReset;
    }
    
    
}
