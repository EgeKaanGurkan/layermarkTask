package com.example.layermarktask.book;

import com.example.layermarktask.customer.Customer;
import com.example.layermarktask.customer.CustomerRepository;
import com.example.layermarktask.exception.ApiRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class BookService {

    private BookRepository bookRepository;
    private CustomerRepository customerRepository;

    public BookService(BookRepository bookRepository, CustomerRepository customerRepository) {
        this.bookRepository = bookRepository;
        this.customerRepository = customerRepository;
    }

    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    public Book loanBook(Long bookId, Long userId) {

        Customer customer = customerRepository
                .findById(userId)
                .orElseThrow(() -> new ApiRequestException("No user with id " + userId + " exists.", HttpStatus.NOT_FOUND));

        Book book = bookRepository
                .findById(bookId)
                .orElseThrow(() -> new ApiRequestException("No book with id " + bookId + " exists.", HttpStatus.NOT_FOUND));

        if (book.isOnLoan()) {
            throw new ApiRequestException("The book with id " + bookId + " is already on loan.", HttpStatus.BAD_REQUEST);
        }

        book.setLoaner(customer);
        book.setOnLoan(true);

        return bookRepository.save(book);
    }

    public Book returnBook(Long bookId, Long userId) {

        Customer customer = customerRepository
                .findById(userId)
                .orElseThrow(() -> new ApiRequestException("No book with id " + userId + " exists.", HttpStatus.NOT_FOUND));

        Book book = bookRepository
                .findById(bookId)
                .orElseThrow(() -> new ApiRequestException("No book with id " + userId + " exists.", HttpStatus.NOT_FOUND));

        if (!book.isOnLoan()) {
            throw new ApiRequestException("This book has not been loaned!", HttpStatus.BAD_REQUEST);
        }

        System.out.println(book.getLoaner().getId());
        System.out.println(customer.getId());

        if (!Objects.equals(book.getLoaner().getId(), customer.getId())) {
            throw new ApiRequestException("The book with id " + bookId + " is not loaned by this user.", HttpStatus.BAD_REQUEST);
        }

        book.setLoaner(null);
        book.setOnLoan(false);
        return bookRepository.save(book);
    }

    public Optional<Book> getBookById(Long bookId) {
        return bookRepository.findById(bookId);
    }
}
