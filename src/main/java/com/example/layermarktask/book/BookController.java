package com.example.layermarktask.book;

import com.example.layermarktask.exception.ApiRequestException;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/books")
public class BookController {

    private BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<List<Book>> getBooks() {
        return new ResponseEntity<List<Book>>(this.bookService.getBooks(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Book> postBook(@Valid @RequestBody Book book) {
        return new ResponseEntity<Book>(this.bookService.addBook(book), HttpStatus.CREATED);
    }

    @GetMapping(path = "{bookId}")
    public ResponseEntity<Book> getBookById(@PathVariable Long bookId) {
        Book book = bookService.getBookById(bookId).orElseThrow(
                () -> new ApiRequestException("No book with the given ID exists.", HttpStatus.NOT_FOUND)
        );
        return new ResponseEntity<Book>(book, HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping(path = "loan")
    public ResponseEntity<Book> loanBook(@RequestParam Long bookId, @RequestParam Long userId) {
        return new ResponseEntity<Book>(this.bookService.loanBook(bookId, userId), HttpStatus.CREATED);
    }

    @PutMapping(path = "return")
    public Book returnBook(@RequestParam Long bookId, @RequestParam Long userId) {
        return this.bookService.returnBook(bookId, userId);
    }
}
