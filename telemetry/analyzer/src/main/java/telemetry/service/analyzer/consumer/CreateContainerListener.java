package telemetry.service.analyzer.consumer;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.HashMap;
import java.util.Map;

public class CreateContainerListener {

    public static <V> ConsumerFactory<String, V> createConsumerFactory(
            String bootstrapServers,
            String groupId,
            String keyDeserializer,
            String valueDeserializer
    ) throws ClassNotFoundException {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, Class.forName(keyDeserializer));
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, Class.forName(valueDeserializer));
        props.put("specific.avro.reader", true);

        return new DefaultKafkaConsumerFactory<>(props);
    }

    public static <V> ConcurrentKafkaListenerContainerFactory<String, V> createListenerContainerFactory(
            ConsumerFactory<String, V> consumerFactory
    ) {
        ConcurrentKafkaListenerContainerFactory<String, V> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }
}
