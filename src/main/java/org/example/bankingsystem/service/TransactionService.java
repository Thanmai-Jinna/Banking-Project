package org.example.bankingsystem.service;

import org.example.bankingsystem.dao.impl.TransactionDAOImpl;
import org.example.bankingsystem.model.Transaction;

import java.util.List;

public class TransactionService {
    private final TransactionDAOImpl dao = new TransactionDAOImpl();

    public Transaction create(Transaction t) { return dao.create(t); }
    public Transaction findById(Long id) { return dao.findById(id); }
    public List<Transaction> findByAccountId(Long accountId) { return dao.findByAccountId(accountId); }
    public List<Transaction> findAll() { return dao.findAll(); }
}
