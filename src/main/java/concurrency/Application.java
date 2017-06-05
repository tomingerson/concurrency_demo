package concurrency;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Created by tom on 03.06.2017.
 */
@SpringBootApplication(scanBasePackages = {"concurrency/motivation"})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
