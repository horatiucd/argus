package com.hcd.argus.review;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
public class Review {

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

    public enum Status {
        DRAFT, OPEN, CLOSED, CANCELLED
    }
}
