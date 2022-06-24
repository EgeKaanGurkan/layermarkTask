package com.example.layermarktask.librarian;

import com.example.layermarktask.exception.ApiRequestException;
import com.example.layermarktask.library.Library;
import com.example.layermarktask.library.LibraryRepository;
import com.example.layermarktask.role.Role;
import com.example.layermarktask.role.RoleRepository;
import com.example.layermarktask.user.UserRepository;
import com.example.layermarktask.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class LibrarianService {

    private LibrarianRepository librarianRepository;
    private LibraryRepository libraryRepository;
    private PasswordEncoder passwordEncoder;
    private RoleRepository roleRepository;


    public LibrarianService(LibrarianRepository librarianRepository, LibraryRepository libraryRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.librarianRepository = librarianRepository;
        this.libraryRepository = libraryRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    public List<Librarian> getAllLibrarians() {
        return librarianRepository.findAll();
    }

    public Librarian registerLibrarian(Librarian librarian) {
        Optional<Librarian> librarianOptional = librarianRepository.findByEmail(librarian.getEmail());
        if (librarianOptional.isPresent()) {
            throw new ApiRequestException("Email already registered", HttpStatus.BAD_REQUEST);
        }

        librarian.setPassword(passwordEncoder.encode(librarian.getPassword()));

        Librarian savedLibrarian = librarianRepository.save(librarian);

        this.addRoleToLibrarian(librarian.getEmail(), "ROLE_LIBRARIAN");

        return savedLibrarian;
    }

    public Librarian assignToALibrary(Long librarianId, Long libraryId) {
        Librarian librarian = librarianRepository
                .findById(librarianId)
                .orElseThrow(() -> new ApiRequestException("A librarian with the given ID does not exist", HttpStatus.NOT_FOUND));

        Library library = libraryRepository
                .findById(libraryId)
                .orElseThrow(() -> new ApiRequestException("A library with the given ID does not exist", HttpStatus.NOT_FOUND));

        librarian.setLibrary(library);

        return librarianRepository.save(librarian);
    }

    public void addRoleToLibrarian(String email, String roleName) {
        Librarian librarian = librarianRepository.findByEmail(email).orElseThrow(() -> new ApiRequestException("No user with that email exists.", HttpStatus.NOT_FOUND));

        Role role = roleRepository.findByName(roleName);

        librarian.getRoles().add(role);

    }
}
