package ee.mihkel.rendipood.controller;

import ee.mihkel.rendipood.entity.Customer;
import ee.mihkel.rendipood.entity.Film;
import ee.mihkel.rendipood.entity.FilmType;
import ee.mihkel.rendipood.repository.CustomerRepository;
import ee.mihkel.rendipood.repository.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CustomerController {

    @Autowired
    CustomerRepository customerRepository;

    @PostMapping("customers")
    public List<Customer> addCustomer(@RequestBody Customer customer) {
        customerRepository.save(customer);
        return customerRepository.findAll();
    }

    @DeleteMapping("customers/{id}")
    public List<Customer> deleteCustomer(@PathVariable Long id) {
        customerRepository.deleteById(id);
        return customerRepository.findAll();
    }


    @GetMapping("customers")
    public List<Customer> allCustomers() {
        return customerRepository.findAll();
    }

}
