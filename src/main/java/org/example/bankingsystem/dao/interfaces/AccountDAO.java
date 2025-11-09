package org.example.bankingsystem.dao.interfaces;

import org.example.bankingsystem.model.Account;
import java.util.List;

public interface AccountDAO {
    Account create(Account account);
    Account findById(Long id);
    Account findByAccountNumber(String accountNumber);
    List<Account> findAll();
    boolean update(Account account);
    boolean delete(Long id);
}
