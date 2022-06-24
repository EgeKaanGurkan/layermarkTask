package com.example.layermarktask.customer;


import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@RestController
@RequestMapping("api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService userService) {
        this.customerService = userService;
    }

    @GetMapping
    public ResponseEntity<List<Customer>> getCustomers() {
        return new ResponseEntity<List<Customer>>(customerService.getCustomers(), HttpStatus.OK);
    }

    @PostMapping("register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Customer> postCustomer(@RequestBody Customer user) {
        return new ResponseEntity<Customer>(customerService.addUser(user), HttpStatus.CREATED);
    }

    @PostMapping("add-role")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> addRoleToCustomer(@Valid @RequestBody RoleToUserForm roleToUserForm) {
        customerService.addRoleToCustomer(roleToUserForm.getEmail(), roleToUserForm.getRoleName());
        return ResponseEntity.ok().build();
    }

}

@Data
class RoleToUserForm {
    @NotEmpty
    private String email;

    @NotEmpty
    private String roleName;
}
