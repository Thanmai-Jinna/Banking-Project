package org.example.bankingsystem.model;

import java.math.BigDecimal;

public class Account {
    private Long id;
    private Long customerId;
    private String accountNumber;
    private BigDecimal balance;
    private String type;

    public Account() {}

    public Account(Long id, Long customerId, String accountNumber, BigDecimal balance, String type) {
        this.id = id;
        this.customerId = customerId;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.type = type;
    }

    public Account(Long customerId, String accountNumber, BigDecimal balance, String type) {
        this.customerId = customerId;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.type = type;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }
    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }
    public BigDecimal getBalance() { return balance; }
    public void setBalance(BigDecimal balance) { this.balance = balance; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", customerId=" + customerId +
                ", accountNumber='" + accountNumber + '\'' +
                ", balance=" + balance +
                ", type='" + type + '\'' +
                '}';
    }
}
