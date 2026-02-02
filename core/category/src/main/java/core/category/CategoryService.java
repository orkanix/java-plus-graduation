package core.category;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = {
        "core.category",
        "core.common.exception"
})
@EnableFeignClients(basePackages = "core.common")
public class CategoryService {

    public static void main(String[] args) {
        SpringApplication.run(CategoryService.class, args);
    }
}
