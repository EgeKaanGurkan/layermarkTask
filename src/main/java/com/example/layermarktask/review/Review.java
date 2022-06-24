package com.example.layermarktask.review;

import com.example.layermarktask.book.Book;
import com.example.layermarktask.customer.Customer;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity(name = "Review")
@Table
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private int rating;

    @ManyToOne
    @JoinColumn(name = "reviewer_id")
    @JsonIgnore
    private Customer reviewer;

    @ManyToOne
    @JoinColumn(name = "reviewed_book_id")
    @JsonIgnore
    private Book reviewedBook;

    public Review(String content, int rating) {
        this.content = content;
        this.rating = rating;
    }

    public Review() {

    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Customer getReviewer() {
        return reviewer;
    }

    public void setReviewer(Customer reviewer) {
        this.reviewer = reviewer;
    }

    public Book getReviewedBook() {
        return reviewedBook;
    }

    public void setReviewedBook(Book reviewedBook) {
        this.reviewedBook = reviewedBook;
    }
}
