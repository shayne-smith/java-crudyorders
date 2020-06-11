package com.lambdaschool.javaorders.services;

import com.lambdaschool.javaorders.models.Customer;
import com.lambdaschool.javaorders.models.Order;
import com.lambdaschool.javaorders.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "customerService")
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository custrepos;

    @Override
    public List<Customer> listAllCustomerOrders() {
        List<Customer> rtnList = new ArrayList<>();

        custrepos.findAll()
                .iterator()
                .forEachRemaining(rtnList::add);

        return rtnList;
    }

    @Override
    public Customer findCustomerById(long id) {
        return custrepos.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer " + id + " Not Found"));
    }

    @Override
    public List<Customer> listCustomersByLikeName(String thename) {
        return custrepos.findByCustnameContainingIgnoringCase(thename);
    }

    // DELETE
    @Transactional
    @Override
    public void delete(long custcode)
    {
        if (custrepos.findById(custcode).isPresent()) {
            custrepos.deleteById(custcode);
        } else {
            throw new EntityNotFoundException("Customer " + custcode + " Not Found");
        }
    }

    // POST / PUT
    @Transactional
    @Override
    public Customer save(Customer customer)
    {
        Customer newCustomer = new Customer();

        if (customer.getCustcode() != 0) {
            // PUT
            custrepos.findById(customer.getCustcode())
                .orElseThrow(() -> new EntityNotFoundException("Customer " + customer.getCustcode() + " Not Found"));

            newCustomer.setCustcode(customer.getCustcode());
        }

        newCustomer.setCustcity(customer.getCustcity());
        newCustomer.setCustcountry(customer.getCustcountry());
        newCustomer.setCustname(customer.getCustname());
        newCustomer.setGrade(customer.getGrade());
        newCustomer.setOpeningamt(customer.getOpeningamt());
        newCustomer.setOutstandingamt(customer.getOutstandingamt());
        newCustomer.setPaymentamt(customer.getPaymentamt());
        newCustomer.setPhone(customer.getPhone());
        newCustomer.setReceiveamt(customer.getReceiveamt());
        newCustomer.setWorkingarea(customer.getWorkingarea());
        newCustomer.setAgent(customer.getAgent());

        newCustomer.getOrders().clear();
        for (Order o : customer.getOrders()) {
            Order newOrder = new Order(o.getAdvanceamount(), o.getOrdamount(), o.getOrderdescription(), newCustomer);
            newCustomer.getOrders().add(newOrder);
        }

        return custrepos.save(newCustomer);
    }

    // PATCH
    @Transactional
    @Override
    public Customer update(
        Customer customer,
        long custcode)
    {
        Customer currentCustomer = custrepos.findById(custcode)
            .orElseThrow(() -> new EntityNotFoundException("Customer " + custcode + " Not Found"));

        if (customer.getCustcity() != null) {
            currentCustomer.setCustcity(customer.getCustcity());
        }

        if (customer.getCustcountry() != null) {
            currentCustomer.setCustcountry(customer.getCustcountry());
        }

        if (customer.getCustname() != null) {
            currentCustomer.setCustname(customer.getCustname());
        }

        if (customer.getGrade() != null) {
            currentCustomer.setGrade(customer.getGrade());
        }

        if (customer.hasvalueforopeningamt) {
            currentCustomer.setOpeningamt(customer.getOpeningamt());
        }

        if (customer.hasvalueforoutstandingamt) {
            currentCustomer.setOutstandingamt(customer.getOutstandingamt());
        }

        if (customer.hasvalueforpaymentamt) {
            currentCustomer.setPaymentamt(customer.getPaymentamt());
        }

        if (customer.getPhone() != null) {
            currentCustomer.setPhone(customer.getPhone());
        }

        if (customer.hasvalueforreceiveamt) {
            currentCustomer.setReceiveamt(customer.getReceiveamt());
        }

        if (customer.getWorkingarea() != null) {
            currentCustomer.setWorkingarea(customer.getWorkingarea());
        }

        if (customer.getAgent() != null) {
            currentCustomer.setAgent(customer.getAgent());
        }

        if (customer.getOrders().size() > 0) {
            currentCustomer.getOrders().clear();

            for (Order o : customer.getOrders()) {
                Order newOrder = new Order (o.getAdvanceamount(), o.getOrdamount(), o.getOrderdescription(), currentCustomer);
                currentCustomer.getOrders().add(newOrder);
            }
        }

        return custrepos.save(currentCustomer);
    }
}
