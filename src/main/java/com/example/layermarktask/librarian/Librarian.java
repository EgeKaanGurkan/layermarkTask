package com.example.layermarktask.librarian;

import com.example.layermarktask.library.Library;
import com.example.layermarktask.user.User;

import javax.persistence.*;
import java.time.LocalDate;

@Entity(name = "Librarian")
public class Librarian extends User {

    @OneToOne
    @JoinColumn(name = "library_id")
    private Library library;

    public Librarian(String email, String firstName, String lastName, LocalDate dateOfBirth, String password) {
        super(email, firstName, lastName, dateOfBirth, password);
    }

    public Librarian() {

    }

    public Library getLibrary() {
        return library;
    }

    public void setLibrary(Library library) {
        this.library = library;
    }
}
