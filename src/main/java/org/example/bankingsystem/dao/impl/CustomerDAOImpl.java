package org.example.bankingsystem.dao.impl;

import org.example.bankingsystem.dao.interfaces.CustomerDAO;
import org.example.bankingsystem.model.Customer;
import org.example.bankingsystem.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAOImpl implements CustomerDAO {

    @Override
    public Customer create(Customer customer) {
        String sql = "INSERT INTO customers (name, email, phone) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, customer.getName());
            ps.setString(2, customer.getEmail());
            ps.setString(3, customer.getPhone());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    customer.setId(rs.getLong(1));
                }
            }
            return customer;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Customer findById(Long id) {
        String sql = "SELECT id, name, email, phone FROM customers WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Customer(rs.getLong("id"), rs.getString("name"), rs.getString("email"), rs.getString("phone"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public List<Customer> findAll() {
        String sql = "SELECT id, name, email, phone FROM customers";
        List<Customer> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Customer(rs.getLong("id"), rs.getString("name"), rs.getString("email"), rs.getString("phone")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public boolean update(Customer customer) {
        String sql = "UPDATE customers SET name=?, email=?, phone=? WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, customer.getName());
            ps.setString(2, customer.getEmail());
            ps.setString(3, customer.getPhone());
            ps.setLong(4, customer.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String sql = "DELETE FROM customers WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
