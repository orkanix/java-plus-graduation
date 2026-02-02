package core.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = {
        "core.user",
        "core.common.exception"
})
@EnableFeignClients(basePackages = "core.common")
public class UserService {

    public static void main(String[] args) {
        SpringApplication.run(UserService.class, args);
    }
}
