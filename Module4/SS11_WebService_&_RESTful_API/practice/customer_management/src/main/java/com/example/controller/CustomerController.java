package com.example.controller;

import com.example.entity.Customer;
import com.example.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    //-------------------Retrieve All Customers--------------------------------------------------------

    @GetMapping("/customers")
    public ResponseEntity<List<Customer>> listAllCustomers() {
        List<Customer> customers = customerService.findAll();
        if (customers.isEmpty()) {
            return new ResponseEntity<List<Customer>>( HttpStatus.NO_CONTENT );//You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<Customer>>( customers, HttpStatus.OK );
    }

    //-------------------Retrieve Single Customer--------------------------------------------------------

    @GetMapping(value = "/customers/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Customer> getCustomer(@PathVariable("id") Integer id) {
        System.out.println( "Fetching Customer with id " + id );
        Customer customer = customerService.findById( id );
        if (customer == null) {
            System.out.println( "Customer with id " + id + " not found" );
            return new ResponseEntity<Customer>( HttpStatus.NOT_FOUND );
        }
        return new ResponseEntity<Customer>( customer, HttpStatus.OK );
    }

    //-------------------Create a Customer--------------------------------------------------------

    @PostMapping("/customers/")
    public ResponseEntity<Void> createCustomer(@RequestBody Customer customer, UriComponentsBuilder uriComponentsBuilder) {
        System.out.println( "Creating Customer " + customer.getLastName() );
        customerService.save( customer );
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uriComponentsBuilder.path( "/customers/{id}" ).buildAndExpand( customer.getId() ).toUri() );
        return new ResponseEntity<Void>( headers, HttpStatus.CREATED );
    }

    //------------------- Update a Customer --------------------------------------------------------

    @PutMapping("/customers/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable("id") Integer id, @RequestBody Customer customer) {
        System.out.println( "Updating Customer " + id );

        Customer currentCustomer = customerService.findById( id );

        if (currentCustomer == null) {
            System.out.println( "Customer with id " + id + " not found" );
            return new ResponseEntity<Customer>( HttpStatus.NOT_FOUND );
        }

        currentCustomer.setFirstName( customer.getFirstName() );
        currentCustomer.setLastName( customer.getLastName() );
        currentCustomer.setId( customer.getId() );

        customerService.save( currentCustomer );
        return new ResponseEntity<Customer>( currentCustomer, HttpStatus.OK );
    }

    //------------------- Delete a Customer --------------------------------------------------------

    @DeleteMapping("/customers/{id}")
    public ResponseEntity<Customer> deleteCustomer(@PathVariable("id") Integer id) {
        System.out.println( "Fetching & Deleting Customer with id " + id );

        Customer customer = customerService.findById( id );
        if (customer == null) {
            System.out.println( "Unable to delete. Customer with id " + id + " not found" );
            return new ResponseEntity<Customer>( HttpStatus.NOT_FOUND );
        }

        customerService.remove( id );
        return new ResponseEntity<Customer>( HttpStatus.NO_CONTENT );
    }
}

