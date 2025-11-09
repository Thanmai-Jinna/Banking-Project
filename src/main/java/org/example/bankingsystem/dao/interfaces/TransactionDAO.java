package org.example.bankingsystem.dao.interfaces;

import org.example.bankingsystem.model.Transaction;
import java.util.List;

public interface TransactionDAO {
    Transaction create(Transaction transaction);
    Transaction findById(Long id);
    List<Transaction> findByAccountId(Long accountId);
    List<Transaction> findAll();
}
