package core.event;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication(scanBasePackages = {
        "core.event",
        "ru.practicum.ewm",
        "core.common"
})
@EnableFeignClients(basePackages = "core.common")
public class EventService {

    public static void main(String[] args) {
        SpringApplication.run(EventService.class, args);
    }
}
