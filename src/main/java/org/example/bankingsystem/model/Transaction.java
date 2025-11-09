package org.example.bankingsystem.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Transaction {
    private Long id;
    private Long accountId;
    private BigDecimal amount;
    private String type;
    private Timestamp timestamp;
    private String description;

    public Transaction() {}

    public Transaction(Long id, Long accountId, BigDecimal amount, String type, Timestamp timestamp, String description) {
        this.id = id;
        this.accountId = accountId;
        this.amount = amount;
        this.type = type;
        this.timestamp = timestamp;
        this.description = description;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getAccountId() { return accountId; }
    public void setAccountId(Long accountId) { this.accountId = accountId; }
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public Timestamp getTimestamp() { return timestamp; }
    public void setTimestamp(Timestamp timestamp) { this.timestamp = timestamp; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", accountId=" + accountId +
                ", amount=" + amount +
                ", type='" + type + '\'' +
                ", timestamp=" + timestamp +
                ", description='" + description + '\'' +
                '}';
    }
}
