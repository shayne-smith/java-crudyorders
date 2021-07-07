package com.lambdaschool.javaorders.services;

import com.lambdaschool.javaorders.models.Order;


public interface OrderService {

    Order findOrderById(long id);

    // DELETE
    void delete(long id);

    // POST / PUT
    Order save(Order order);

}
