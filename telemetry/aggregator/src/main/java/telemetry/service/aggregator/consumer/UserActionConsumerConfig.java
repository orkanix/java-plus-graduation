package telemetry.service.aggregator.consumer;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import ru.practicum.ewm.stats.avro.UserActionAvro;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class UserActionConsumerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.consumer.key-deserializer}")
    private String keyDeserializer;

    @Value("${spring.kafka.consumer.value-deserializer}")
    private String valueDeserializer;

    @Bean
    public ConsumerFactory<String, UserActionAvro> userActionConsumerFactory() throws ClassNotFoundException {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "aggregator-group");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, Class.forName(keyDeserializer));
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, Class.forName(valueDeserializer));
        return new DefaultKafkaConsumerFactory<>(props);
    }
}
