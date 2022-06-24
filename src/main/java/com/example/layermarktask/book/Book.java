package com.example.layermarktask.book;

import com.example.layermarktask.customer.Customer;
import com.example.layermarktask.library.Library;
import com.example.layermarktask.review.Review;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "Book")
@Table
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotEmpty
    private String name;
    @NotEmpty
    private String isbn;
    @NotNull
    private Integer pageNumber;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDateTime publishedAt;
    @NotEmpty
    private String genre;

    private boolean onLoan = false;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    @JsonIgnore
    private Customer loaner;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "library_id", referencedColumnName = "id")
    @JsonIgnore
    private Library library;

    @OneToMany(mappedBy = "reviewedBook")
    private Set<Review> reviews = new HashSet<>();

    public Book(String name, String isbn, Integer pageNumber, LocalDateTime publishedAt, String genre) {
        this.name = name;
        this.isbn = isbn;
        this.pageNumber = pageNumber;
        this.publishedAt = publishedAt;
        this.genre = genre;
    }

    public Book() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public LocalDateTime getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(LocalDateTime publishedAt) {
        this.publishedAt = publishedAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setLoaner(Customer loaner) {
        this.loaner = loaner;
    }

    public Customer getLoaner() {
        return loaner;
    }

    public Long getId() {
        return id;
    }

    public boolean isOnLoan() {
        return onLoan;
    }

    public void setOnLoan(boolean onLoan) {
        this.onLoan = onLoan;
    }

    public Library getLibrary() {
        return library;
    }

    public void setLibrary(Library library) {
        this.library = library;
    }

    public Set<Review> getReviews() {
        return reviews;
    }

    public void setReviews(Set<Review> reviews) {
        this.reviews = reviews;
    }
}
