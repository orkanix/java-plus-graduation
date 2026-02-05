package telemetry.service.aggregator.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.stats.avro.EventSimilarityAvro;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SimilarityProducer {

    private final KafkaTemplate<String, EventSimilarityAvro> similarityKafkaTemplate;

    @Value("${spring.topics.events-similarity-topic-name}")
    private String similarityTopic;

    public void sendSimilarity(EventSimilarityAvro similarEventsRequest) {
        similarityKafkaTemplate.send(similarityTopic, similarEventsRequest);
        log.info("Отправлен снапшот similarity={}", similarEventsRequest.getSchema());
    }

    public void sendSimilarity(List<EventSimilarityAvro> similarEvents) {
        for (EventSimilarityAvro ev : similarEvents) {
            similarityKafkaTemplate.send(similarityTopic, ev);
            log.info("Отправлен similarity={}", ev);
        }
        similarityKafkaTemplate.flush(); // чтобы сразу отправить все
    }

    public void flush() {
        similarityKafkaTemplate.flush();
    }
}
