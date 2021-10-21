package com.hcd.argus.review;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Optional;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
public class Review implements Sunsetable {

    @Id
    @GeneratedValue
    private Long id;
    private String description;
    private Status status;

    private LocalDateTime dateCreated;
    private LocalDateTime dateOpened;
    private LocalDateTime dateClosed;
    private LocalDateTime dateCancelled;

    public Review(Status status,
                  String description) {
        this.description = description;
        this.status = status;
        dateCreated = LocalDateTime.now();
    }

    @Override
    public Optional<LocalDateTime> sunsetDate() {
        return switch (status) {
            case DRAFT -> Optional.of(dateCreated.plusDays(2));
            case CANCELLED -> Optional.of(dateCancelled.plusYears(1));
            default -> Optional.empty();
        };
    }

    public enum Status {
        DRAFT, OPEN, CLOSED, CANCELLED
    }
}
