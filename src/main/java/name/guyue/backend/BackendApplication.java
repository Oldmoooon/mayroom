package name.guyue.backend;

import name.guyue.backend.db.DemoUserRepository;
import name.guyue.backend.model.DemoUser;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication public class BackendApplication {

    public static void main(String... args) {
        SpringApplication.run(BackendApplication.class, args);
    }
}
