package telemetry.service.collector.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.stats.avro.UserActionAvro;
import telemetry.service.collector.producer.UserActionProducer;

@Service
@RequiredArgsConstructor
public class CollectorService {

    private final UserActionProducer producer;

    public void sendUserAction(UserActionAvro action) {
        producer.sendAction(action);
    }
}
