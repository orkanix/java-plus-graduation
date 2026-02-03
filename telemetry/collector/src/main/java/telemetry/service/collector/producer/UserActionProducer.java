package telemetry.service.collector.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserActionProducer {

    protected final KafkaTemplate<String, SpecificRecordBase> producer;

    @Value("${spring.topics.user-action-topic-name}")
    private String userActionTopic;

    public void sendAction(SpecificRecordBase userAction) {
        ProducerRecord<String, SpecificRecordBase> record = new ProducerRecord<>(
                userActionTopic,
                userAction
        );

        producer.send(record);
        producer.flush();
    }
}
