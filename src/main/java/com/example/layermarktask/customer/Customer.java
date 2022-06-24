package com.example.layermarktask.customer;

import com.example.layermarktask.book.Book;
import com.example.layermarktask.review.Review;
import com.example.layermarktask.user.User;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.*;

@Entity(name = "Customer")
@Table
public class Customer extends User {

    @OneToMany(mappedBy = "loaner")
    private Set<Book> borrowedBooks = new HashSet<>();

    @OneToMany(mappedBy = "reviewer")
    private Set<Review> reviews = new HashSet<>();

    public Customer(String email, String firstName, String lastName, LocalDate dateOfBirth, String password) {
        super(email, firstName, lastName, dateOfBirth, password);
    }

    public Customer() {

    }

    public Set<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    public Set<Review> getReviews() {
        return reviews;
    }

    public void setReviews(Set<Review> reviews) {
        this.reviews = reviews;
    }
}
