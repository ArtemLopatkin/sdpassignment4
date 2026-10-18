package com.company;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Bike {
    private final String id;
    private final String model;
    private final BigDecimal hourlyRate;

    public Bike(String id, String model, BigDecimal hourlyRate) {
        if (id == null || id.trim().isEmpty() || model == null || model.trim().isEmpty()) {
            throw new RentalException("Bike ID and model are required.");
        }
        if (hourlyRate == null || hourlyRate.signum() <= 0 || hourlyRate.scale() > 2) {
            throw new RentalException("Hourly rate must be positive with at most two decimal places.");
        }
        this.id = id;
        this.model = model;
        this.hourlyRate = hourlyRate.setScale(2, RoundingMode.UNNECESSARY);
    }

    public String getId() {
        return id;
    }

    public BigDecimal getHourlyRate() {
        return hourlyRate;
    }

    public String describe() {
        return id + " | " + model + " | " + hourlyRate + " KZT/hour";
    }
}
