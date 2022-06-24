package com.example.layermarktask.library;

import com.example.layermarktask.book.Book;
import com.example.layermarktask.book.BookRepository;
import com.example.layermarktask.exception.ApiRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibraryService {

    private LibraryRepository libraryRepository;
    private BookRepository bookRepository;

    @Autowired
    public LibraryService(LibraryRepository libraryRepository, BookRepository bookRepository) {
        this.libraryRepository = libraryRepository;
        this.bookRepository = bookRepository;
    }

    public List<Library> getLibraries() {
        return libraryRepository.findAll();
    }

    public Library addLibrary(Library library) {
        return libraryRepository.save(library);
    }

    public Book assignBookToLibrary(Long libraryId, Long bookId) {
        Library library = libraryRepository
                .findById(libraryId)
                .orElseThrow(() -> new ApiRequestException("No library with id " + libraryId + " exists.", HttpStatus.NOT_FOUND));

        Book book = bookRepository
                .findById(bookId)
                .orElseThrow(() -> new ApiRequestException("No book with id " + bookId + " exists.", HttpStatus.NOT_FOUND));

        if (book.getLibrary() != null) {
            throw new ApiRequestException("The given book is already assigned to library: " + library.getName() + ", ID: " + library.getId(), HttpStatus.BAD_REQUEST);
        }

        book.setLibrary(library);

        return bookRepository.save(book);
    }
}
