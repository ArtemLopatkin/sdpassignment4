package com.company;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

public class PaymentService {
    private final Map<String, BigDecimal> balances = new HashMap<>();
    private final Map<String, Payment> payments = new HashMap<>();
    private int nextPaymentId = 1;

    public void addAccount(String customerId, BigDecimal balance) {
        if (customerId == null || customerId.trim().isEmpty()) {
            throw new RentalException("Customer ID is required.");
        }
        if (balance == null || balance.signum() < 0 || balance.scale() > 2) {
            throw new RentalException("Balance must be non-negative with at most two decimal places.");
        }
        if (balances.putIfAbsent(customerId, balance.setScale(2, RoundingMode.UNNECESSARY)) != null) {
            throw new RentalException("Payment account already exists: " + customerId);
        }
    }

    public BigDecimal getBalance(String customerId) {
        BigDecimal balance = balances.get(customerId);
        if (balance == null) {
            throw new RentalException("Payment account not found: " + customerId);
        }
        return balance;
    }

    public String charge(String customerId, BigDecimal amount) {
        if (amount == null || amount.signum() <= 0 || amount.scale() > 2) {
            throw new RentalException("Payment must be positive with at most two decimal places.");
        }
        BigDecimal balance = getBalance(customerId);
        if (balance.compareTo(amount) < 0) {
            throw new RentalException("Insufficient funds. Required: " + amount + " KZT, balance: " + balance + " KZT.");
        }
        String paymentId = "P" + nextPaymentId++;
        balances.put(customerId, balance.subtract(amount));
        payments.put(paymentId, new Payment(customerId, amount));
        return paymentId;
    }

    public void refund(String paymentId) {
        Payment payment = payments.get(paymentId);
        if (payment == null || payment.refunded) {
            throw new RentalException("Payment does not exist or was already refunded: " + paymentId);
        }
        balances.put(payment.customerId, getBalance(payment.customerId).add(payment.amount));
        payment.refunded = true;
    }

    private static class Payment {
        private final String customerId;
        private final BigDecimal amount;
        private boolean refunded;

        private Payment(String customerId, BigDecimal amount) {
            this.customerId = customerId;
            this.amount = amount;
        }
    }
}
