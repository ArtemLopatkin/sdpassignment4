package com.company;

import java.math.BigDecimal;

public class RentalPricing {
    public BigDecimal calculatePrice(Bike bike, int hours) {
        if (bike == null) {
            throw new RentalException("Bike is required to calculate the price.");
        }
        Rental.validateDuration(hours);
        return bike.getHourlyRate().multiply(BigDecimal.valueOf(hours));
    }
}
