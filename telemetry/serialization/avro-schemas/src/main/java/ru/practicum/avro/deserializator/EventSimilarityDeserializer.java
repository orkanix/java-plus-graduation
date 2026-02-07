package ru.practicum.avro.deserializator;

import org.springframework.stereotype.Component;
import ru.practicum.ewm.stats.avro.EventSimilarityAvro;

@Component
public class EventSimilarityDeserializer extends BaseAvroDeserializer<EventSimilarityAvro> {

    public EventSimilarityDeserializer() {
        super(EventSimilarityAvro.getClassSchema());
    }
}
