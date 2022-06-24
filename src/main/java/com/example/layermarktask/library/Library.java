package com.example.layermarktask.library;

import com.example.layermarktask.book.Book;
import com.example.layermarktask.librarian.Librarian;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "Library")
@Table
public class Library {

    @Id
    @GeneratedValue
    private Long id;

    @NotEmpty(message = "City field should not be empty.")
    private String city;

    @NotEmpty(message = "Country field should not be empty.")
    private String country;

    @NotEmpty(message = "Name field should not be empty.")
    private String name;

    @OneToMany(mappedBy = "library")
    private Set<Book> books = new HashSet<>();

    @OneToOne(mappedBy = "library")
    private Librarian librarian;

    public Library(String city, String country, String name) {
        this.city = city;
        this.country = country;
        this.name = name;
    }

    public Library() {

    }

    public Long getId() {
        return id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Book> getBooks() {
        return books;
    }

    @Override
    public String toString() {
        return "Library{" +
                "id=" + id +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", name='" + name + '\'' +
                ", books=" + books +
                '}';
    }

    public Librarian getLibrarian() {
        return librarian;
    }

    public void setLibrarian(Librarian librarian) {
        this.librarian = librarian;
    }
}
