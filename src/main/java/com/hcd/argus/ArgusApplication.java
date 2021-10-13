package com.hcd.argus;

import com.hcd.argus.review.Review;
import com.hcd.argus.review.ReviewRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
@Slf4j
public class ArgusApplication {

    public static void main(String[] args) {
        SpringApplication.run(ArgusApplication.class, args);
    }

    @Bean
    CommandLineRunner init(ReviewRepository repository) {
        return args -> {
            Review draft = new Review(Review.Status.DRAFT, "Draft");

            Review open = new Review(Review.Status.OPEN, "Open");
            open.setDateOpened(open.getDateCreated().plusMinutes(5));

            Review cancelled = new Review(Review.Status.CANCELLED, "Cancelled");
            cancelled.setDateOpened(cancelled.getDateCreated().plusMinutes(5));
            cancelled.setDateCancelled(cancelled.getDateOpened().plusMinutes(10));

            Review closed = new Review(Review.Status.CLOSED, "Closed");
            closed.setDateOpened(closed.getDateCreated().plusMinutes(10));
            closed.setDateClosed(closed.getDateOpened().plusMinutes(15));

            List.of(draft, open, closed, cancelled)
                    .forEach(review -> log.info("Persisted " + repository.save(review)));
        };
    }
}
