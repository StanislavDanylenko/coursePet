package stanislav.danylenko.coursepet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.concurrent.atomic.AtomicLong;

@SpringBootApplication
public class CoursepetApplication {

    public static void main(String[] args) {
        SpringApplication.run(CoursepetApplication.class, args);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public AtomicLong smartCardIdCreator() {
        return new AtomicLong(0);
    }
}
