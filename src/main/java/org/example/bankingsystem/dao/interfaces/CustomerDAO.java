package org.example.bankingsystem.dao.interfaces;

import org.example.bankingsystem.model.Customer;
import java.util.List;

public interface CustomerDAO {
    Customer create(Customer customer);
    Customer findById(Long id);
    List<Customer> findAll();
    boolean update(Customer customer);
    boolean delete(Long id);
}
