package com.example.layermarktask.library;

import com.example.layermarktask.book.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/v1/libraries")
public class LibraryController {

    private LibraryService libraryService;

    @Autowired
    public LibraryController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @GetMapping
    public List<Library> getLibraries() {
        return this.libraryService.getLibraries();
    }

    @PostMapping
    public Library postLibrary(@RequestBody @Valid Library library) {
        return libraryService.addLibrary(library);
    }

    @PutMapping("assign-book")
    public ResponseEntity<Book> assignBookToLibrary(@RequestParam Long libraryId, @RequestParam Long bookId) {
        return new ResponseEntity<Book>(this.libraryService.assignBookToLibrary(libraryId, bookId), HttpStatus.CREATED);
    }

}
