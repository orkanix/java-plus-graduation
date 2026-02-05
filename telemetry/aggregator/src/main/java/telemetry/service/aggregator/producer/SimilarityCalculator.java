package telemetry.service.aggregator.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.stats.avro.ActionTypeAvro;
import ru.practicum.ewm.stats.avro.EventSimilarityAvro;
import ru.practicum.ewm.stats.avro.UserActionAvro;

import java.time.Instant;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class SimilarityCalculator {

    private final Map<Long, Map<Long, Double>> eventUserWeights = new HashMap<>();
    private final Map<Long, Double> eventWeightSums = new HashMap<>();
    private final Map<Long, Map<Long, Double>> minWeightsSum = new HashMap<>();

    public List<EventSimilarityAvro> process(UserActionAvro event) {
        List<EventSimilarityAvro> results = new ArrayList<>();

        long userId = event.getUserId();
        long eventId = event.getEventId();
        double weight = getActionWeight(event.getActionType());
        Instant timestamp = event.getTimestamp();

        Map<Long, Double> userWeights = eventUserWeights.computeIfAbsent(eventId, e -> new HashMap<>());
        Double oldWeight = userWeights.get(userId);

        if (oldWeight != null && oldWeight >= weight) {
            return results; // ничего не меняется
        }

        userWeights.put(userId, weight);
        double delta = weight - (oldWeight == null ? 0 : oldWeight);
        eventWeightSums.merge(eventId, delta, Double::sum);

        for (Long otherEventId : eventUserWeights.keySet()) {
            if (otherEventId.equals(eventId)) continue;

            Map<Long, Double> otherUsers = eventUserWeights.get(otherEventId);
            Double otherWeight = otherUsers.get(userId);
            if (otherWeight == null) continue;

            double oldMin = Math.min(oldWeight == null ? 0 : oldWeight, otherWeight);
            double newMin = Math.min(weight, otherWeight);
            double minDelta = newMin - oldMin;

            addMinSum(eventId, otherEventId, minDelta);

            double sMin = getMinSum(eventId, otherEventId);
            double sA = eventWeightSums.getOrDefault(eventId, 0.0);
            double sB = eventWeightSums.getOrDefault(otherEventId, 0.0);

            if (sA == 0 || sB == 0) continue;

            double similarity = sMin / (Math.sqrt(sA) * Math.sqrt(sB));
            results.add(generateSimilarity(eventId, otherEventId, similarity, timestamp));
        }

        return results;
    }

    private EventSimilarityAvro generateSimilarity(long a, long b, double score, Instant timestamp) {
        long first = Math.min(a, b);
        long second = Math.max(a, b);

        EventSimilarityAvro msg = EventSimilarityAvro.newBuilder()
                .setEventA((int) first)
                .setEventB((int) second)
                .setScore(score)
                .setTimestamp(timestamp)
                .build();

        log.info("Сформировал объект similarity: {}", msg);
        return msg;
    }

    private void addMinSum(long a, long b, double delta) {
        long first = Math.min(a, b);
        long second = Math.max(a, b);

        minWeightsSum
                .computeIfAbsent(first, e -> new HashMap<>())
                .merge(second, delta, Double::sum);
    }

    private double getMinSum(long a, long b) {
        long first = Math.min(a, b);
        long second = Math.max(a, b);

        return minWeightsSum
                .getOrDefault(first, Map.of())
                .getOrDefault(second, 0.0);
    }

    private double getActionWeight(ActionTypeAvro action) {
        return switch (action) {
            case VIEW -> 0.4;
            case REGISTER -> 0.8;
            case LIKE -> 1.0;
        };
    }
}
