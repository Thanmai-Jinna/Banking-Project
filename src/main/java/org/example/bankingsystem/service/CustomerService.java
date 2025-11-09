package org.example.bankingsystem.service;

import org.example.bankingsystem.dao.impl.CustomerDAOImpl;
import org.example.bankingsystem.model.Customer;
import java.util.List;

public class CustomerService {
    private final CustomerDAOImpl dao = new CustomerDAOImpl();

    public Customer create(Customer c) { return dao.create(c); }
    public Customer findById(Long id) { return dao.findById(id); }
    public List<Customer> findAll() { return dao.findAll(); }
    public boolean update(Customer c) { return dao.update(c); }
    public boolean delete(Long id) { return dao.delete(id); }
}
