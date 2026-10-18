package com.company;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

public class RentalService {
    private final Map<String, Rental> rentals = new LinkedHashMap<>();
    private int nextRentalId = 1;

    public void checkNoActiveRental(String customerId) {
        for (Rental rental : rentals.values()) {
            if (rental.isActive() && rental.getCustomerId().equals(customerId)) {
                throw new RentalException("Customer already has an active rental: " + customerId);
            }
        }
    }

    public Rental createRental(Customer customer, Bike bike, int hours, BigDecimal totalPrice, String paymentId) {
        if (customer == null || bike == null || !customer.canRent()) {
            throw new RentalException("An eligible customer and a bike are required.");
        }
        Rental.validateDuration(hours);
        if (totalPrice == null || totalPrice.signum() <= 0
                || paymentId == null || paymentId.trim().isEmpty()) {
            throw new RentalException("Valid rental duration, price and payment are required.");
        }
        checkNoActiveRental(customer.getId());
        for (Rental rental : rentals.values()) {
            if (rental.isActive() && rental.getBikeId().equals(bike.getId())) {
                throw new RentalException("Bike already has an active rental: " + bike.getId());
            }
        }
        String rentalId = "R" + nextRentalId++;
        Rental rental = new Rental(rentalId, customer.getId(), bike.getId(), hours, totalPrice, paymentId);
        rentals.put(rentalId, rental);
        return rental;
    }

    public Rental findRental(String rentalId) {
        Rental rental = rentals.get(rentalId);
        if (rental == null) {
            throw new RentalException("Rental not found: " + rentalId);
        }
        return rental;
    }

    public Rental findActiveRental(String rentalId) {
        Rental rental = findRental(rentalId);
        if (!rental.isActive()) {
            throw new RentalException("Rental is already closed: " + rentalId);
        }
        return rental;
    }

    public void closeRental(String rentalId) {
        findActiveRental(rentalId).close();
    }
}
