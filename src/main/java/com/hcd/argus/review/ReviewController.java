package com.hcd.argus.review;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static org.springframework.hateoas.CollectionModel.of;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.http.ResponseEntity.ok;

@RestController
public class ReviewController {

    private final ReviewService service;
    private final ReviewModelAssembler assembler;
    private final ApplicationEventPublisher eventPublisher;

    @Autowired
    public ReviewController(ReviewService service,
                            ReviewModelAssembler assembler,
                            ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
        this.service = service;
        this.assembler = assembler;
    }

    @GetMapping("/reviews")
    public ResponseEntity<?> all(HttpServletResponse response) {
        final List<Review> reviews = service.findAll();

        List<EntityModel<Review>> content = reviews.stream()
                .map(assembler::toModel)
                .toList();

        Link link = linkTo(methodOn(getClass()).all(response)).withSelfRel();

        return ResponseEntity.ok()
                .body(of(content, link));
    }

    @GetMapping("/reviews/search")
    public ResponseEntity<?> search(@RequestParam(name = "filter") String filter) {
        final List<Review> reviews = service.search(filter);

        List<EntityModel<Review>> content = reviews.stream()
                .map(assembler::toModel)
                .toList();

        Link link = linkTo(methodOn(getClass()).search(filter)).withSelfRel();

        return ResponseEntity.ok()
                .body(of(content, link));
    }

    @GetMapping("/reviews/{id}")
    public ResponseEntity<?> one(HttpServletResponse response, @PathVariable Long id) {
        Review review = service.findOne(id);
        return ResponseEntity.ok(assembler.toModel(review));
    }

    @PutMapping("/reviews/{id}/open")
    public ResponseEntity<?> open(@PathVariable Long id) {
        final Review review = service.open(id);
        return ok(assembler.toModel(review));
    }

    @PutMapping("/reviews/{id}/cancel")
    public ResponseEntity<?> cancel(@PathVariable Long id) {
        final Review review = service.cancel(id);
        return ok(assembler.toModel(review));
    }

    @PutMapping("/reviews/{id}/close")
    public ResponseEntity<?> close(@PathVariable Long id) {
        final Review review = service.close(id);
        return ok(assembler.toModel(review));
    }
}
