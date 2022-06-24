package com.example.layermarktask.populate;

import com.example.layermarktask.book.Book;
import com.example.layermarktask.book.BookRepository;
import com.example.layermarktask.customer.Customer;
import com.example.layermarktask.customer.CustomerService;
import com.example.layermarktask.librarian.Librarian;
import com.example.layermarktask.librarian.LibrarianService;
import com.example.layermarktask.library.Library;
import com.example.layermarktask.library.LibraryRepository;
import com.example.layermarktask.review.Review;
import com.example.layermarktask.review.ReviewRepository;
import com.example.layermarktask.role.Role;
import com.example.layermarktask.role.RoleRepository;
import com.github.javafaker.Faker;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.concurrent.TimeUnit;

@Service
public class PopulateService {

    private RoleRepository roleRepository;
    private LibraryRepository libraryRepository;
    private CustomerService customerService;
    private BookRepository bookRepository;
    private ReviewRepository reviewRepository;
    private LibrarianService librarianService;

    public PopulateService(RoleRepository roleRepository, LibraryRepository libraryRepository, CustomerService customerService, BookRepository bookRepository, ReviewRepository reviewRepository, LibrarianService librarianService) {
        this.roleRepository = roleRepository;
        this.libraryRepository = libraryRepository;
        this.customerService = customerService;
        this.bookRepository = bookRepository;
        this.reviewRepository = reviewRepository;
        this.librarianService = librarianService;
    }

    public void populate() {
        Faker faker = new Faker();
        roleRepository.save(new Role("ROLE_CUSTOMER"));
        roleRepository.save(new Role("ROLE_LIBRARIAN"));
        roleRepository.save(new Role("ROLE_ADMIN"));

        for (int i = 0; i < 3; i++) {
            Library library = new Library(
                    faker.address().city(),
                    faker.address().country(),
                    faker.university().name() + " Library"
            );

            libraryRepository.save(library);
        }

        for (int i = 0; i < 5; i++) {
            Customer user = new Customer(faker.internet().emailAddress(), faker.name().firstName(), faker.name().lastName(), LocalDate.of(2000, Month.JANUARY, 5), "123qwe");
            customerService.addUser(user);
        }

        Customer user = new Customer(
                "test@admin.com",
                "Test",
                "Admin",
                LocalDate.of(2000, Month.JANUARY, 5),
                "123qwe");
        customerService.addUser(user);
        customerService.addRoleToCustomer(user.getEmail(), "ROLE_ADMIN");

        user = new Customer(
                "test@customer.com",
                "Test",
                "Admin",
                LocalDate.of(2000, Month.JANUARY, 5),
                "123qwe");
        customerService.addUser(user);

        for (int i = 0; i < 10; i++) {
            Book book = new Book(
                    faker.book().title(),
                    "9783161484100",
                    (int) faker.number().randomNumber(3, true),
                    faker.date().past(10, 4, TimeUnit.DAYS).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime(),
                    faker.book().genre()
            );

            bookRepository.save(book);
        }

        Book book = new Book(
                "Book With Reviews",
                "9783161484100",
                (int) faker.number().randomNumber(3, true),
                faker.date().past(10, 4, TimeUnit.DAYS).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime(),
                faker.book().genre()
        );
        bookRepository.save(book);

        for (int i = 0; i < 10; i++) {
            Review review = new Review(
                    faker.lorem().sentence(10),
                    faker.random().nextInt(1, 10)
            );
            review.setReviewer(user);
            review.setReviewedBook(book);

            reviewRepository.save(review);
        }

        for (int i = 0; i < 10; i++) {
            Librarian librarian = new Librarian(faker.internet().emailAddress(), faker.name().firstName(), faker.name().lastName(), LocalDate.of(2000, Month.JANUARY, 5), "123qwe");
            librarianService.registerLibrarian(librarian);
        }

        Librarian librarian = new Librarian("test@librarian.com", faker.name().firstName(), faker.name().lastName(), LocalDate.of(2000, Month.JANUARY, 5), "123qwe");
        librarianService.registerLibrarian(librarian);

    }

}
