package com.example.layermarktask.role;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/roles")
public class RoleController {

    private RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public ResponseEntity<List<Role>> getRoles() {
        return new ResponseEntity<List<Role>>(roleService.getAllRoles(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Role> postRole(@RequestBody Role role) {
        return new ResponseEntity<Role>(roleService.saveRole(role), HttpStatus.CREATED);
    }

}
