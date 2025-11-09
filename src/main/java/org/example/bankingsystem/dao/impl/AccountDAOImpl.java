package org.example.bankingsystem.dao.impl;

import org.example.bankingsystem.dao.interfaces.AccountDAO;
import org.example.bankingsystem.model.Account;
import org.example.bankingsystem.util.DBConnection;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDAOImpl implements AccountDAO {

    @Override
    public Account create(Account account) {
        String sql = "INSERT INTO accounts (customer_id, account_number, balance, type) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, account.getCustomerId());
            ps.setString(2, account.getAccountNumber());
            ps.setBigDecimal(3, account.getBalance() == null ? BigDecimal.ZERO : account.getBalance());
            ps.setString(4, account.getType());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    account.setId(rs.getLong(1));
                }
            }
            return account;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Account findById(Long id) {
        String sql = "SELECT id, customer_id, account_number, balance, type FROM accounts WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return map(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Account findByAccountNumber(String accountNumber) {
        String sql = "SELECT id, customer_id, account_number, balance, type FROM accounts WHERE account_number = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, accountNumber);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return map(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public List<Account> findAll() {
        String sql = "SELECT id, customer_id, account_number, balance, type FROM accounts";
        List<Account> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(map(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public boolean update(Account account) {
        String sql = "UPDATE accounts SET customer_id=?, account_number=?, balance=?, type=? WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, account.getCustomerId());
            ps.setString(2, account.getAccountNumber());
            ps.setBigDecimal(3, account.getBalance());
            ps.setString(4, account.getType());
            ps.setLong(5, account.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String sql = "DELETE FROM accounts WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Account map(ResultSet rs) throws SQLException {
        Account a = new Account();
        a.setId(rs.getLong("id"));
        a.setCustomerId(rs.getLong("customer_id"));
        a.setAccountNumber(rs.getString("account_number"));
        a.setBalance(rs.getBigDecimal("balance"));
        a.setType(rs.getString("type"));
        return a;
    }
}
