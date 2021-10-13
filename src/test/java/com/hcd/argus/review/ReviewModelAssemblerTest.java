package com.hcd.argus.review;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

import java.util.Optional;

class ReviewModelAssemblerTest {

    private ReviewModelAssembler modelAssembler;

    @BeforeEach
    public void setup() {
        modelAssembler = new ReviewModelAssembler();
    }

    @Test
    void toModel_draftReview() {
        final long id = 10L;
        final Review review = new Review(Review.Status.DRAFT, "Review");
        review.setId(id);

        final EntityModel<Review> model = modelAssembler.toModel(review);

        Assertions.assertTrue(model.getLinks().hasSize(4));

        assertForAllStatuses(review, model);

        final Optional<Link> one = model.getLink("open");
        Assertions.assertTrue(one.isPresent());
        Assertions.assertEquals("/reviews/" + review.getId() + "/open", one.get().getHref());
    }

    @Test
    void toModel_openReview() {
        final long id = 10L;
        final Review review = new Review(Review.Status.OPEN, "Review");
        review.setId(id);

        final EntityModel<Review> model = modelAssembler.toModel(review);

        Assertions.assertTrue(model.getLinks().hasSize(5));

        assertForAllStatuses(review, model);

        final Optional<Link> close = model.getLink("close");
        Assertions.assertTrue(close.isPresent());
        Assertions.assertEquals("/reviews/" + review.getId() + "/close", close.get().getHref());

        final Optional<Link> cancel = model.getLink("cancel");
        Assertions.assertTrue(cancel.isPresent());
        Assertions.assertEquals("/reviews/" + review.getId() + "/cancel", cancel.get().getHref());
    }

    @Test
    void toModel_closedReview() {
        final long id = 10L;
        final Review review = new Review(Review.Status.CLOSED, "Review");
        review.setId(id);

        final EntityModel<Review> model = modelAssembler.toModel(review);

        Assertions.assertTrue(model.getLinks().hasSize(3));

        assertForAllStatuses(review, model);
    }

    @Test
    void toModel_cancelledReview() {
        final long id = 10L;
        final Review review = new Review(Review.Status.CANCELLED, "Review");
        review.setId(id);

        final EntityModel<Review> model = modelAssembler.toModel(review);

        Assertions.assertTrue(model.getLinks().hasSize(3));

        assertForAllStatuses(review, model);
    }

    private void assertForAllStatuses(Review review, EntityModel<Review> model) {
        final Optional<Link> one = model.getLink("self");
        Assertions.assertTrue(one.isPresent());
        Assertions.assertEquals("/reviews/" + review.getId(), one.get().getHref());

        final Optional<Link> all = model.getLink("reviews");
        Assertions.assertTrue(all.isPresent());
        Assertions.assertEquals("/reviews", all.get().getHref());

        final Optional<Link> search = model.getLink("search");
        Assertions.assertTrue(search.isPresent());
        Assertions.assertEquals("/reviews/search?filter=pattern", search.get().getHref());
    }
}
