package com.example.layermarktask.review;

import com.example.layermarktask.book.Book;
import com.example.layermarktask.book.BookRepository;
import com.example.layermarktask.customer.Customer;
import com.example.layermarktask.customer.CustomerRepository;
import com.example.layermarktask.exception.ApiRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ReviewService {

    private ReviewRepository reviewRepository;
    private CustomerRepository customerRepository;
    private BookRepository bookRepository;

    public ReviewService(ReviewRepository reviewRepository, CustomerRepository customerRepository, BookRepository bookRepository) {
        this.reviewRepository = reviewRepository;
        this.customerRepository = customerRepository;
        this.bookRepository = bookRepository;
    }

    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    public Optional<List<Review>> getReviewsByCustomer(String customerEmail) {
        Customer customer = customerRepository.findCustomerByEmail(customerEmail).orElseThrow(() -> new ApiRequestException("No customer with the given id could be found", HttpStatus.NOT_FOUND));
        return reviewRepository.findByReviewer(customer);
    }

    public List<Review> getReviewsByBookIdAndReviewerEmail(Long bookId, String reviewerEmail) {
        return reviewRepository.findAllByReviewedBookIdAndReviewerEmail(bookId, reviewerEmail).get();
    }

    public List<Review> getReviewsByReviewedBookId(Long bookId) {
        return reviewRepository.findAllByReviewedBookId(bookId).get();
    }

    public List<Review> getReviewsByReviewerEmail(String email) {
        return reviewRepository.findAllByReviewerEmail(email).get();
    }
    public Review addReview(String userEmail, Long bookId, String content, int rating) {

        Customer customer = customerRepository.findCustomerByEmail(userEmail)
                .orElseThrow(() -> new ApiRequestException("No customer with the given email exists.", HttpStatus.NOT_FOUND));

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ApiRequestException("No book with the given ID exists.", HttpStatus.NOT_FOUND));

        Review review = new Review(content, rating);

        review.setReviewer(customer);
        review.setReviewedBook(book);

        return reviewRepository.save(review);

    }
}
