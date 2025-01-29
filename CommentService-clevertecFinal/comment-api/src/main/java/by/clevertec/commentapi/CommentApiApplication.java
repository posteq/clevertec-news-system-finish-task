package by.clevertec.commentapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"by.clevertec.commentapi", "by.clevertec.commentcore"})
@EnableJpaRepositories
@EntityScan
public class CommentApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommentApiApplication.class, args);
    }

}
