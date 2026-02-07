package telemetry.service.analyzer.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.practicum.ewm.stats.avro.EventSimilarityAvro;
import ru.practicum.ewm.stats.avro.UserActionAvro;
import telemetry.service.analyzer.model.EventSimilarity;
import telemetry.service.analyzer.model.UserInteraction;
import telemetry.service.analyzer.repository.EventSimilarityRepository;
import telemetry.service.analyzer.repository.UserInteractionRepository;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class AnalyzerStarter {

    private final UserInteractionRepository userInteractionRepository;
    private final EventSimilarityRepository eventSimilarityRepository;

    @KafkaListener(topics = "${spring.topics.user-action-topic-name}",
            groupId = "analyzer-group",
            containerFactory = "userActionKafkaListenerContainerFactory")
    public synchronized void getUserActions(UserActionAvro userActionAvro) {
        try {
            log.info("Analyzer: Получено действие пользователя: {}", userActionAvro);
            Optional<UserInteraction> existingInteraction =
                    userInteractionRepository.findByUserIdAndEventId(userActionAvro.getUserId(),
                            userActionAvro.getEventId());

            Double score = 0.0;
            switch (userActionAvro.getActionType()) {
                case VIEW -> score = 0.4;
                case REGISTER -> score = 0.8;
                case LIKE -> score = 1.0;
            }

            if (existingInteraction.isPresent()) {
                UserInteraction userInteraction = existingInteraction.get();
                if (userInteraction.getRating() < score) {

                    userInteraction.setRating(score);
                    userInteraction.setTimestamp(
                            LocalDateTime.ofInstant(userActionAvro.getTimestamp(), ZoneId.systemDefault()));

                    UserInteraction userInteraction1 = userInteractionRepository.save(userInteraction);
                    log.info(userInteraction1.toString());
                }
            } else {
                UserInteraction userInteraction = UserInteraction.builder()
                        .userId(userActionAvro.getUserId())
                        .eventId(userActionAvro.getEventId())
                        .rating(score)
                        .timestamp(LocalDateTime.now())
                        .build();

                UserInteraction userInteraction1 = userInteractionRepository.save(userInteraction);
                log.info(userInteraction1.toString());
            }
        } catch (Exception e) {
            log.error("Ошибка при получении данных в UserActionConsumer", e);
        }
    }

    @KafkaListener(topics = "${spring.topics.events-similarity-topic-name}",
            groupId = "analyzer-group",
            containerFactory = "eventSimilarityKafkaListenerContainerFactory")
    public synchronized void getEventSimilarity(EventSimilarityAvro eventSimilarityAvro) {
        try {
            log.info("Analyzer: Получено event-similarity: {}", eventSimilarityAvro);
            Optional<EventSimilarity> existingSimilarity =
                    eventSimilarityRepository.findByEventAAndEventB(eventSimilarityAvro.getEventA(), eventSimilarityAvro.getEventB());

            if (existingSimilarity.isPresent()) {

                EventSimilarity eventSimilarity = existingSimilarity.get();
                eventSimilarity.setScore(eventSimilarityAvro.getScore());
                eventSimilarity.setTimestamp(LocalDateTime.now());

                EventSimilarity eventSimilarity1 = eventSimilarityRepository.save(eventSimilarity);
                log.info(eventSimilarity1.toString());
            } else {

                EventSimilarity similarity = EventSimilarity.builder()
                        .eventA(eventSimilarityAvro.getEventA())
                        .eventB(eventSimilarityAvro.getEventB())
                        .score(eventSimilarityAvro.getScore())
                        .timestamp(LocalDateTime.now())
                        .build();

                EventSimilarity eventSimilarity1 = eventSimilarityRepository.save(similarity);
                log.info(eventSimilarity1.toString());
            }
        } catch (Exception e) {
            log.error("Ошибка при получении данных в EventSimilarityConsumer", e);
        }
    }
}

