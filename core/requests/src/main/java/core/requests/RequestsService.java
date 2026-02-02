package core.requests;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = {
        "core.requests",
        "core.common.exception"
})
@EnableFeignClients(basePackages = "core.common")
public class RequestsService {

    public static void main(String[] args) {
        SpringApplication.run(RequestsService.class, args);
    }
}

