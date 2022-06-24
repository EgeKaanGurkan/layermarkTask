package com.example.layermarktask.librarian;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
class AssignLibrarianToLibraryRequest {
    @NotNull(message = "libraryId should be sent")
    private Long libraryId;

    @NotNull(message = "librarianId should be sent")
    private Long librarianId;
}

@Data
class RoleToLibrarianForm {
    private String email;
    private String roleName;
}
