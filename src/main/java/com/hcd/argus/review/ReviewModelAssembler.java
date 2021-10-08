package com.hcd.argus.review;

import com.hcd.argus.review.Review.Status;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ReviewModelAssembler implements RepresentationModelAssembler<Review, EntityModel<Review>> {

    @Override
    public EntityModel<Review> toModel(Review entity) {
        EntityModel<Review> model = EntityModel.of(entity);

        model.add(linkTo(methodOn(ReviewController.class).one(null, entity.getId())).withSelfRel(),
                linkTo(methodOn(ReviewController.class).all(null)).withRel("reviews"),
                linkTo(methodOn(ReviewController.class).search("pattern")).withRel("search"));

        if (entity.getStatus() == Status.DRAFT) {
            model.add(linkTo(methodOn(ReviewController.class).open(entity.getId())).withRel("open"));
        } else if (entity.getStatus() == Status.OPEN) {
            model.add(linkTo(methodOn(ReviewController.class).close(entity.getId())).withRel("close"));
            model.add(linkTo(methodOn(ReviewController.class).cancel(entity.getId())).withRel("cancel"));
        }

        return model;
    }
}
