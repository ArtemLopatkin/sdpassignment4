package com.company;

import java.math.BigDecimal;

public class Rental {
    private static final int MAX_RENTAL_HOURS = 24;
    private final String id;
    private final String customerId;
    private final String bikeId;
    private final int hours;
    private final BigDecimal totalPrice;
    private final String paymentId;
    private boolean active = true;

    Rental(String id, String customerId, String bikeId, int hours, BigDecimal totalPrice, String paymentId) {
        this.id = id;
        this.customerId = customerId;
        this.bikeId = bikeId;
        this.hours = hours;
        this.totalPrice = totalPrice;
        this.paymentId = paymentId;
    }

    static void validateDuration(int hours) {
        if (hours < 1 || hours > MAX_RENTAL_HOURS) {
            throw new RentalException("Rental duration must be between 1 and 24 hours.");
        }
    }

    public String getId() {
        return id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getBikeId() {
        return bikeId;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public boolean isActive() {
        return active;
    }

    void close() {
        active = false;
    }

    public String describe() {
        return id + " | customer: " + customerId + " | bike: " + bikeId
                + " | hours: " + hours + " | total: " + totalPrice + " KZT"
                + " | " + (active ? "ACTIVE" : "CLOSED");
    }
}
