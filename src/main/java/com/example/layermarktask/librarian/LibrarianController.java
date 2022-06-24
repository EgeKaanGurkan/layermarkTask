package com.example.layermarktask.librarian;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/v1/librarians")
public class LibrarianController {

    private LibrarianService librarianService;

    public LibrarianController(LibrarianService librarianService) {
        this.librarianService = librarianService;
    }

    @GetMapping
    public ResponseEntity<List<Librarian>> getLibrarians() {
        return new ResponseEntity<List<Librarian>>(librarianService.getAllLibrarians(), HttpStatus.OK);
    }

    @PostMapping(path = "register")
    public ResponseEntity<Librarian> registerLibrarian(@Valid @RequestBody Librarian librarian) {
        return new ResponseEntity<Librarian>(librarianService.registerLibrarian(librarian), HttpStatus.CREATED);
    }

    @PostMapping("assign-to-library")
    public ResponseEntity<Librarian> assignToLibrary(@Valid @RequestBody AssignLibrarianToLibraryRequest assignLibrarianToLibraryRequest) {
        return new ResponseEntity<Librarian>(
                this.librarianService.assignToALibrary(
                        assignLibrarianToLibraryRequest.getLibrarianId(),
                        assignLibrarianToLibraryRequest.getLibraryId()
                ), HttpStatus.CREATED);
    }

    @PostMapping("add-role")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> addRoleToCustomer(@RequestBody RoleToLibrarianForm roleToLibrarianForm) {
        librarianService.addRoleToLibrarian(roleToLibrarianForm.getEmail(), roleToLibrarianForm.getRoleName());
        return ResponseEntity.ok().build();
    }

}