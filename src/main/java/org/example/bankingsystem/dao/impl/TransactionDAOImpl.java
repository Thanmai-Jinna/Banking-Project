package org.example.bankingsystem.dao.impl;

import org.example.bankingsystem.dao.interfaces.TransactionDAO;
import org.example.bankingsystem.model.Transaction;
import org.example.bankingsystem.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAOImpl implements TransactionDAO {

    @Override
    public Transaction create(Transaction transaction) {
        String sql = "INSERT INTO transactions (account_id, amount, type, description) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, transaction.getAccountId());
            ps.setBigDecimal(2, transaction.getAmount());
            ps.setString(3, transaction.getType());
            ps.setString(4, transaction.getDescription());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    transaction.setId(rs.getLong(1));
                }
            }
            return transaction;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Transaction findById(Long id) {
        String sql = "SELECT id, account_id, amount, type, timestamp, description FROM transactions WHERE id = ?";
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
    public List<Transaction> findByAccountId(Long accountId) {
        String sql = "SELECT id, account_id, amount, type, timestamp, description FROM transactions WHERE account_id = ? ORDER BY timestamp DESC";
        List<Transaction> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, accountId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(map(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public List<Transaction> findAll() {
        String sql = "SELECT id, account_id, amount, type, timestamp, description FROM transactions ORDER BY timestamp DESC";
        List<Transaction> list = new ArrayList<>();
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

    private Transaction map(ResultSet rs) throws SQLException {
        Transaction t = new Transaction();
        t.setId(rs.getLong("id"));
        t.setAccountId(rs.getLong("account_id"));
        t.setAmount(rs.getBigDecimal("amount"));
        t.setType(rs.getString("type"));
        t.setTimestamp(rs.getTimestamp("timestamp"));
        t.setDescription(rs.getString("description"));
        return t;
    }
}
