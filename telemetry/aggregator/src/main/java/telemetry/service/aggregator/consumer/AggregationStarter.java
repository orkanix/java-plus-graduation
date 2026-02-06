package telemetry.service.aggregator.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.practicum.ewm.stats.avro.EventSimilarityAvro;
import ru.practicum.ewm.stats.avro.UserActionAvro;
import telemetry.service.aggregator.producer.SimilarityProducer;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class AggregationStarter {

    private final telemetry.service.aggregator.consumer.SimilarityCalculator similarityCalculator;
    private final SimilarityProducer similarityProducer;

    @KafkaListener(topics = "${spring.topics.user-action-topic-name}",
            groupId = "aggregator-group")
    public synchronized void start(UserActionAvro event) {
        try {
            log.info("Получено действие пользователя: userId={}, eventId={}, action={}",
                    event.getUserId(),
                    event.getEventId(),
                    event.getActionType());

            List<EventSimilarityAvro> similarity = similarityCalculator.process(event);

            if (similarity != null && !similarity.isEmpty()) {
                similarityProducer.sendSimilarity(similarity);

                for (EventSimilarityAvro sim : similarity) {
                    log.info("Сходство успешно отправлено: eventA={}, eventB={}, score={}",
                            sim.getEventA(),
                            sim.getEventB(),
                            sim.getScore());
                }
            }
        } catch (Exception e) {
            log.error("Ошибка при обработке действия пользователя: userId={}, eventId={}, action={}",
                    event.getUserId(),
                    event.getEventId(),
                    event.getActionType(),
                    e);
            throw e;
        }
    }
}