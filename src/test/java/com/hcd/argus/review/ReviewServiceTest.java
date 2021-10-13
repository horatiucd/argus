package com.hcd.argus.review;

import com.hcd.argus.exception.EntityNotFoundException;
import com.hcd.argus.exception.OperationNotAllowedException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

class ReviewServiceTest {

    private ReviewRepository mockRepository;

    private ReviewService reviewService;

    @BeforeEach
    void before() {
        mockRepository = Mockito.mock(ReviewRepository.class);
        reviewService = new ReviewService(mockRepository);
    }

    @Test
    void findAll() {
        final Review review = new Review();
        review.setId(10L);
        final List<Review> expectedReviews = List.of(review);

        when(mockRepository.findAll())
                .thenReturn(expectedReviews);

        List<Review> actualReviews = reviewService.findAll();
        Assertions.assertEquals(expectedReviews, actualReviews);

        verify(mockRepository).findAll();
        verifyNoMoreInteractions(mockRepository);
    }

    @Test
    void findOne_notFound() {
        final long id = 1L;
        when(mockRepository.findById(id))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFoundException.class,
                () -> reviewService.findOne(id));

        verify(mockRepository).findById(id);
        verifyNoMoreInteractions(mockRepository);
    }

    @Test
    void findOne() {
        final Review expectedReview = new Review();
        expectedReview.setId(10L);

        when(mockRepository.findById(expectedReview.getId()))
                .thenReturn(Optional.of(expectedReview));

        Review actualReview = reviewService.findOne(expectedReview.getId());
        Assertions.assertEquals(expectedReview, actualReview);

        verify(mockRepository).findById(expectedReview.getId());
        verifyNoMoreInteractions(mockRepository);
    }

    @Test
    void open_notAllowed() {
        final long id = 10L;
        final Review reviewToOpen = new Review(Review.Status.OPEN, "Open Review");
        reviewToOpen.setId(id);

        when(mockRepository.findById(reviewToOpen.getId()))
                .thenReturn(Optional.of(reviewToOpen));

        OperationNotAllowedException ex = Assertions.assertThrows(OperationNotAllowedException.class,
                () -> reviewService.open(id));
        Assertions.assertEquals(reviewToOpen.getStatus() + " reviews cannot be opened.", ex.getMessage());

        verify(mockRepository).findById(reviewToOpen.getId());
        verifyNoMoreInteractions(mockRepository);
    }

    @Test
    void open() {
        final Review reviewToOpen = new Review(Review.Status.DRAFT, "Review");
        reviewToOpen.setId(20L);

        Assertions.assertNull(reviewToOpen.getDateOpened());

        when(mockRepository.findById(reviewToOpen.getId()))
                .thenReturn(Optional.of(reviewToOpen));

        when(mockRepository.save(reviewToOpen))
                .thenReturn(reviewToOpen);

        Review review = reviewService.open(reviewToOpen.getId());
        Assertions.assertEquals(Review.Status.OPEN, review.getStatus());
        Assertions.assertNotNull(review.getDateOpened());

        verify(mockRepository).findById(reviewToOpen.getId());
        verify(mockRepository).save(reviewToOpen);
        verifyNoMoreInteractions(mockRepository);
    }

    @Test
    void close_notAllowed() {
        final long id = 20L;
        final Review reviewToClose = new Review(Review.Status.DRAFT, "Review");
        reviewToClose.setId(id);

        when(mockRepository.findById(id))
                .thenReturn(Optional.of(reviewToClose));

        OperationNotAllowedException ex = Assertions.assertThrows(OperationNotAllowedException.class,
                () -> reviewService.close(id));
        Assertions.assertEquals(reviewToClose.getStatus() + " reviews cannot be closed.", ex.getMessage());

        verify(mockRepository).findById(id);
        verifyNoMoreInteractions(mockRepository);
    }

    @Test
    void close() {
        final long id = 20L;
        final Review reviewToClose = new Review(Review.Status.OPEN, "Review");
        reviewToClose.setId(id);

        Assertions.assertNull(reviewToClose.getDateClosed());

        when(mockRepository.findById(id))
                .thenReturn(Optional.of(reviewToClose));

        when(mockRepository.save(reviewToClose))
                .thenReturn(reviewToClose);

        reviewService.close(id);
        Assertions.assertEquals(Review.Status.CLOSED, reviewToClose.getStatus());
        Assertions.assertNotNull(reviewToClose.getDateClosed());

        verify(mockRepository).findById(id);
        verify(mockRepository).save(reviewToClose);
        verifyNoMoreInteractions(mockRepository);
    }

    @Test
    void cancel_notAllowed() {
        final long id = 20L;
        final Review reviewToCancel = new Review(Review.Status.DRAFT, "Review");
        reviewToCancel.setId(id);

        when(mockRepository.findById(id))
                .thenReturn(Optional.of(reviewToCancel));

        OperationNotAllowedException ex = Assertions.assertThrows(OperationNotAllowedException.class,
                () -> reviewService.cancel(id));
        Assertions.assertEquals(reviewToCancel.getStatus() + " reviews cannot be cancelled.", ex.getMessage());

        verify(mockRepository).findById(id);
        verifyNoMoreInteractions(mockRepository);
    }

    @Test
    void cancel() {
        final long id = 20L;
        final Review reviewToCancel = new Review(Review.Status.OPEN, "Review");
        reviewToCancel.setId(id);

        Assertions.assertNull(reviewToCancel.getDateCancelled());

        when(mockRepository.findById(id))
                .thenReturn(Optional.of(reviewToCancel));

        when(mockRepository.save(reviewToCancel))
                .thenReturn(reviewToCancel);

        reviewService.cancel(id);
        Assertions.assertEquals(Review.Status.CANCELLED, reviewToCancel.getStatus());
        Assertions.assertNotNull(reviewToCancel.getDateCancelled());

        verify(mockRepository).findById(id);
        verify(mockRepository).save(reviewToCancel);
        verifyNoMoreInteractions(mockRepository);
    }

    @Test
    void search() {
        final String filter = "review";
        final Review review = new Review(Review.Status.OPEN, "Review");
        review.setId(10L);
        List<Review> expected = List.of(review);

        when(mockRepository.findAll(Mockito.any(Example.class)))
                .thenReturn(expected);

        List<Review> result = reviewService.search(filter);
        Assertions.assertEquals(expected, result);

        ArgumentCaptor<Example<Review>> exampleCaptor = ArgumentCaptor.forClass(Example.class);
        verify(mockRepository).findAll(exampleCaptor.capture());
        final Example<Review> example = exampleCaptor.getValue();

        Assertions.assertEquals(filter, (example.getProbe()).getDescription());

        final ExampleMatcher matcher = example.getMatcher();
        Assertions.assertEquals(ExampleMatcher.MatchMode.ANY, matcher.getMatchMode());
        Assertions.assertTrue(matcher.getPropertySpecifiers().hasSpecifierForPath("description"));
        Assertions.assertEquals(ExampleMatcher.NullHandler.IGNORE, matcher.getNullHandler());
        Assertions.assertFalse(matcher.isIgnoreCaseEnabled());

        verifyNoMoreInteractions(mockRepository);
    }
}
