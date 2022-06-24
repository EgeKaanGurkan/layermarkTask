package com.example.layermarktask.review;

import com.example.layermarktask.customer.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Optional<List<Review>> findByReviewer(Customer customer);
    Optional<List<Review>> findAllByReviewedBookId(Long bookId);
    Optional<List<Review>> findAllByReviewerEmail(String reviewerEmail);
    Optional<List<Review>> findAllByReviewedBookIdAndReviewerEmail(Long bookId, String reviewerEmail);
}
