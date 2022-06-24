package com.example.layermarktask.review;

import com.example.layermarktask.exception.ApiRequestException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("api/v1/reviews")
public class ReviewController {

    private ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping
    public ResponseEntity<List<Review>> getAllReviews(@RequestParam(required = false) Long bookId, @RequestParam(required = false) String customerEmail) {

        if (bookId != null && customerEmail != null) {
            return new ResponseEntity<List<Review>>(reviewService.getReviewsByBookIdAndReviewerEmail(bookId, customerEmail), HttpStatus.OK);
        } else if (bookId != null)  {
            return new ResponseEntity<List<Review>>(reviewService.getReviewsByReviewedBookId(bookId), HttpStatus.OK);
        } else if (customerEmail != null)  {
            return new ResponseEntity<List<Review>>(reviewService.getReviewsByReviewerEmail(customerEmail), HttpStatus.OK);
        }

        return new ResponseEntity<List<Review>>(reviewService.getAllReviews(), HttpStatus.OK);
    }

    @GetMapping(path = "customer")
    public ResponseEntity<List<Review>> getReviewsByCustomer(@RequestParam String userEmail) {
        Optional<List<Review>> reviewsOptional = reviewService.getReviewsByCustomer(userEmail);
        if (reviewsOptional.isEmpty()) {
            return new ResponseEntity<List<Review>>(new ArrayList<>(), HttpStatus.OK);
        }

        List<Review> reviews = reviewsOptional.get();

        return new ResponseEntity<List<Review>>(reviews, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Review> reviewBook(@Valid @RequestBody ReviewBookForm reviewBookForm) {
        Review addedReview = reviewService.addReview(
                reviewBookForm.getUserEmail(),
                reviewBookForm.getBookId(),
                reviewBookForm.getContent(),
                reviewBookForm.getRating()
        );

        return new ResponseEntity<Review>(addedReview, HttpStatus.CREATED);
    }

}

@Data
class ReviewBookForm {

    @Email
    private String userEmail;

    @NotNull(message = "bookId parameter should be sent.")
    private Long bookId;

    @NotEmpty(message = "content parameter should be sent.")
    private String content;

    @Min(1)
    @Max(10)
    @NotNull(message = "rating parameter should be sent.")
    private int rating;
}
