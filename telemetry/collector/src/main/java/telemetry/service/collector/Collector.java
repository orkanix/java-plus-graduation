package telemetry.service.collector;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Collector {

    public static void main(String[] args) {
        SpringApplication.run(Collector.class, args);
    }
}

//java -jar tester-0.0.1.jar --tester.execution.mode=AGGREGATION --tester.execution.output.file-path=./report.txt