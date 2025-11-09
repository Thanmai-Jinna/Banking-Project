package org.example.bankingsystem.service;

import org.example.bankingsystem.dao.impl.AccountDAOImpl;
import org.example.bankingsystem.dao.impl.TransactionDAOImpl;
import org.example.bankingsystem.model.Account;
import org.example.bankingsystem.model.Transaction;

import java.math.BigDecimal;
import java.util.List;

public class AccountService {
    private final AccountDAOImpl accountDao = new AccountDAOImpl();
    private final TransactionDAOImpl txDao = new TransactionDAOImpl();

    public Account create(Account a) { return accountDao.create(a); }
    public Account findById(Long id) { return accountDao.findById(id); }
    public Account findByAccountNumber(String accNo) { return accountDao.findByAccountNumber(accNo); }
    public List<Account> findAll() { return accountDao.findAll(); }
    public boolean update(Account a) { return accountDao.update(a); }
    public boolean delete(Long id) { return accountDao.delete(id); }

    // deposit/withdraw with transaction recording
    public boolean deposit(Long accountId, BigDecimal amount, String description) {
        Account a = accountDao.findById(accountId);
        if (a == null) return false;
        a.setBalance(a.getBalance().add(amount));
        boolean ok = accountDao.update(a);
        if (ok) {
            Transaction tx = new Transaction();
            tx.setAccountId(accountId);
            tx.setAmount(amount);
            tx.setType("CREDIT");
            tx.setDescription(description);
            txDao.create(tx);
        }
        return ok;
    }

    public boolean withdraw(Long accountId, BigDecimal amount, String description) {
        Account a = accountDao.findById(accountId);
        if (a == null) return false;
        if (a.getBalance().compareTo(amount) < 0) return false;
        a.setBalance(a.getBalance().subtract(amount));
        boolean ok = accountDao.update(a);
        if (ok) {
            Transaction tx = new Transaction();
            tx.setAccountId(accountId);
            tx.setAmount(amount);
            tx.setType("DEBIT");
            tx.setDescription(description);
            txDao.create(tx);
        }
        return ok;
    }

    public List<Transaction> getTransactions(Long accountId) {
        return txDao.findByAccountId(accountId);
    }
}
