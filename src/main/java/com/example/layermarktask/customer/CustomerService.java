package com.example.layermarktask.customer;

import com.example.layermarktask.exception.ApiRequestException;
import com.example.layermarktask.role.Role;
import com.example.layermarktask.role.RoleRepository;
import com.example.layermarktask.user.UserRepository;
import com.example.layermarktask.user.UserService;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CustomerService extends UserService {

    private CustomerRepository customerRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;

    public CustomerService(UserRepository userRepository, CustomerRepository customerRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        super(userRepository);
        this.customerRepository = customerRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    public Customer addUser(Customer customer) {
        Optional<Customer> customerByEmail = customerRepository.findCustomerByEmail(customer.getEmail());
        if (customerByEmail.isPresent()) {
            throw new ApiRequestException("Email already registered", HttpStatus.BAD_REQUEST);
        }

        customer.setPassword(passwordEncoder.encode(customer.getPassword()));

        Customer savedCustomer = customerRepository.save(customer);

        this.addRoleToCustomer(savedCustomer.getEmail(), "ROLE_CUSTOMER");

        return savedCustomer;
    }

    public void addRoleToCustomer(String email, String roleName) {
        Customer customer = customerRepository.findCustomerByEmail(email).orElseThrow(() -> new ApiRequestException("No user with that email exists.", HttpStatus.NOT_FOUND));

        Role role = roleRepository.findByName(roleName);

        customer.getRoles().add(role);

    }
}