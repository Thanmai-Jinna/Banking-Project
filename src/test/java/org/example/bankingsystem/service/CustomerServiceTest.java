package org.example.bankingsystem.service;

import org.junit.jupiter.api.*;
import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CustomerServiceTest {
    private static Connection connection;

    @BeforeAll
    static void init() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/banking_db", "root", "Root");
        assertNotNull(connection, "Database connection should not be null");
    }

    @AfterAll
    static void cleanup() throws Exception {
        if (connection != null && !connection.isClosed()) connection.close();
    }

    @Test
    @Order(1)
    void testInsertAndDeleteSampleData() throws Exception {
        // Insert sample data depending on entity type
        Statement stmt = connection.createStatement();
        switch ("Customer") {
            case "Customer": 
                stmt.executeUpdate("INSERT INTO customers (name, email, phone) VALUES ('Test User', 'test_customer@example.com', '9999999999')");
                ResultSet rs = stmt.executeQuery("SELECT * FROM customers WHERE email='test_customer@example.com'");
                assertTrue(rs.next(), "Inserted customers should exist in DB");
                stmt.executeUpdate("DELETE FROM customers WHERE email='test_customer@example.com'");
                break;
            case "Account":
                // ensure there's a customer to link to
                stmt.executeUpdate("INSERT INTO customers (name, email, phone) VALUES ('Acct User', 'acct_test@example.com', '8888888888')", Statement.RETURN_GENERATED_KEYS);
                ResultSet rk = stmt.getGeneratedKeys();
                long cid = -1;
                if (rk.next()) cid = rk.getLong(1);
                if (cid==-1) {
                    ResultSet rs2 = stmt.executeQuery("SELECT id FROM customers WHERE email='acct_test@example.com'");
                    if (rs2.next()) cid = rs2.getLong(1);
                }
                PreparedStatement ps = connection.prepareStatement("INSERT INTO accounts (customer_id, account_number, balance, type) VALUES (?, ?, ?, ?)");
                ps.setLong(1, cid);
                ps.setString(2, "ACC" + System.currentTimeMillis());
                ps.setBigDecimal(3, new java.math.BigDecimal("1000.00"));
                ps.setString(4, "Savings");
                ps.executeUpdate();
                // cleanup
                stmt.executeUpdate("DELETE FROM accounts WHERE customer_id = " + cid);
                stmt.executeUpdate("DELETE FROM customers WHERE id = " + cid);
                break;
            case "Transaction":
                // create customer and account then transaction
                stmt.executeUpdate("INSERT INTO customers (name, email, phone) VALUES ('Tx User', 'tx_test@example.com', '7777777777')", Statement.RETURN_GENERATED_KEYS);
                ResultSet r2 = stmt.getGeneratedKeys();
                long cid2 = -1;
                if (r2.next()) cid2 = r2.getLong(1);
                PreparedStatement psAcc = connection.prepareStatement("INSERT INTO account (customer_id, account_number, balance, type) VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
                psAcc.setLong(1, cid2);
                psAcc.setString(2, "TXACC" + System.currentTimeMillis());
                psAcc.setBigDecimal(3, new java.math.BigDecimal("500.00"));
                psAcc.setString(4, "Current");
                psAcc.executeUpdate();
                ResultSet racc = psAcc.getGeneratedKeys();
                long accId = -1;
                if (racc.next()) accId = racc.getLong(1);
                PreparedStatement psTx = connection.prepareStatement("INSERT INTO transactions (account_id, amount, type, description) VALUES (?, ?, ?, ?)");
                psTx.setLong(1, accId);
                psTx.setBigDecimal(2, new java.math.BigDecimal("250.00"));
                psTx.setString(3, "Deposit");
                psTx.setString(4, "Test deposit");
                psTx.executeUpdate();
                // cleanup
                stmt.executeUpdate("DELETE FROM transactions WHERE account_id = " + accId);
                stmt.executeUpdate("DELETE FROM accounts WHERE id = " + accId);
                stmt.executeUpdate("DELETE FROM customers WHERE id = " + cid2);
                break;
            default:
                // basic smoke test for other entities: just assert DB connection
                assertTrue(connection.isValid(2));
        }
    }
}
