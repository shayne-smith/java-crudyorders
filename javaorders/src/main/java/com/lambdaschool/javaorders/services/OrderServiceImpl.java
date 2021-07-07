package com.lambdaschool.javaorders.services;

import com.lambdaschool.javaorders.models.Customer;
import com.lambdaschool.javaorders.models.Order;
import com.lambdaschool.javaorders.models.Payment;
import com.lambdaschool.javaorders.repositories.CustomerRepository;
import com.lambdaschool.javaorders.repositories.OrderRepository;
import com.lambdaschool.javaorders.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;

import javax.persistence.EntityNotFoundException;
import javax.persistence.Transient;

@Service(value = "orderService")
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderrepos;

    @Autowired
    private PaymentRepository payrepos;

    @Autowired
    private CustomerRepository custrepos;

    @Override
    public Order findOrderById(long id) {
        return orderrepos.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order " + id + " Not Found"));
    }

    // DELETE
    @Transactional
    @Override
    public void delete(long id) {
        if (orderrepos.findById(id).isPresent()) {
            orderrepos.deleteById(id);
        } else {
            throw new EntityNotFoundException("Order " + id + " Not Found");
        }
    }

    // POST / PUT
    @Transactional
    @Override
    public Order save(Order order) {

        Order newOrder = new Order();

        if (order.getOrdnum() != 0)
        {
            // put
            orderrepos.findById(order.getOrdnum())
                .orElseThrow(() -> new EntityNotFoundException("Order " + order.getOrdnum() + " Not Found"));

            newOrder.setOrdnum(order.getOrdnum());
        }

        newOrder.setAdvanceamount(order.getAdvanceamount());
        newOrder.setOrdamount(order.getOrdamount());
        newOrder.setOrderdescription(order.getOrderdescription());
        // newOrder.setCustomer(order.getCustomer());
        newOrder.setCustomer(custrepos.findById(order.getCustomer().getCustcode())
            .orElseThrow(() -> new EntityNotFoundException("Customer " + order.getCustomer().getCustcode() + " Not Found")));

        newOrder.getPayments().clear();
        // newRestaurant.setMenus(restaurant.getMenus()); // assigns the pointer
        for (Payment p : order.getPayments())
        {
            Payment newPay = payrepos.findById(p.getPaymentid())
                .orElseThrow(() -> new EntityNotFoundException("Payment " + p.getPaymentid() + " Not Found"));
            newOrder.addPayment(newPay);
        }

        return orderrepos.save(newOrder);
    }


    // POST http://localhost:2019/orders/order
    // PUT http://localhost:2019/orders/order/63
}
