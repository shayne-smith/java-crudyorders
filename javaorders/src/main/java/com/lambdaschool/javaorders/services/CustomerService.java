package com.lambdaschool.javaorders.services;

import com.lambdaschool.javaorders.models.Customer;

import java.util.List;

public interface CustomerService {

    List<Customer> listAllCustomerOrders();

    Customer findCustomerById(long id);

    List<Customer> listCustomersByLikeName(String thename);

    // DELETE
    void delete(long custcode);

    // POST, PUT
    Customer save(Customer customer);

    // PATCH
    Customer update(Customer customer, long custcode);

}
