package com.lambdaschool.javaorders.controllers;

import com.lambdaschool.javaorders.models.Customer;
import com.lambdaschool.javaorders.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    // http://localhost:2019/customers/orders
    @GetMapping(value = "/orders", produces = {"application/json"})
    public ResponseEntity<?> listAllCustomerOrders() {
        List<Customer> customerOrders = customerService.listAllCustomerOrders();
        return new ResponseEntity<>(customerOrders, HttpStatus.OK);
    }

    // http://localhost:2019/customers/customer/7
    @GetMapping(value = "/customer/{id}", produces = {"application/json"})
    public ResponseEntity<?> findCustomerById(@PathVariable long id) {
        Customer c = customerService.findCustomerById(id);
        return new ResponseEntity<>(c, HttpStatus.OK);
    }

    // http://localhost:2019/customers/namelike/mes
    @GetMapping(value = "/namelike/{thename}")
    public ResponseEntity<?> listCustomersByLikeName(@PathVariable String thename) {
        List<Customer> customers = customerService.listCustomersByLikeName(thename);
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    // http://localhost:2019/customers/customer - POST
    @PostMapping(value = "/customer", consumes = {"application/json"})
    public ResponseEntity<?> addNewCustomer(@Valid @RequestBody Customer newCustomer) {
        newCustomer.setCustcode(0);
        newCustomer = customerService.save(newCustomer);

        HttpHeaders responseHeaders = new HttpHeaders();
        // http://localhost:2019/customers/customer/newcustcode
        URI newCustomerURI = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{custcode}")
                .buildAndExpand(newCustomer.getCustcode())
                .toUri();
        responseHeaders.setLocation(newCustomerURI);

        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }

    // http://localhost:2019/customers/customer/{custcode} - PUT
    @PutMapping(value = "/customer/{custcode}", consumes = {"application/json"})
    public ResponseEntity<?> updateFullCustomer(@Valid @RequestBody Customer updateCustomer, @PathVariable long custcode) {
        updateCustomer.setCustcode(custcode);
        customerService.save(updateCustomer);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    // http://localhost:2019/customers/customer/{custcode} - PATCH
    @PatchMapping(value = "/customer/{custcode}",
    consumes = {"application/json"})
    public ResponseEntity<?> updateCustomer(@RequestBody Customer updateCustomer, @PathVariable long custcode) {
        customerService.update(updateCustomer, custcode);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // http://localhost:2019/customers/customer/{custcode} - DELETE
    @DeleteMapping("/customer/{custcode}")
    public ResponseEntity<?> deleteCustomerById(@PathVariable long custcode) {
        customerService.delete(custcode);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
