package com.company;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;

public class BikeStorage {
    private final Map<String, Bike> bikes = new LinkedHashMap<>();
    private final Set<String> rentedBikes = new HashSet<>();

    public void addBike(Bike bike) {
        if (bike == null) {
            throw new RentalException("Bike is required.");
        }
        if (bikes.putIfAbsent(bike.getId(), bike) != null) {
            throw new RentalException("Bike already exists: " + bike.getId());
        }
    }

    public Bike findAvailableBike(String bikeId) {
        Bike bike = findBike(bikeId);
        if (rentedBikes.contains(bikeId)) {
            throw new RentalException("Bike is already rented: " + bikeId);
        }
        return bike;
    }

    public void reserveBike(String bikeId) {
        findAvailableBike(bikeId);
        rentedBikes.add(bikeId);
    }

    public void releaseBike(String bikeId) {
        findBike(bikeId);
        if (!rentedBikes.remove(bikeId)) {
            throw new RentalException("Bike is not rented: " + bikeId);
        }
    }

    public String listAvailableBikes() {
        StringJoiner result = new StringJoiner(System.lineSeparator());
        for (Bike bike : bikes.values()) {
            if (!rentedBikes.contains(bike.getId())) {
                result.add(bike.describe());
            }
        }
        return result.length() == 0 ? "No bikes are available." : result.toString();
    }

    private Bike findBike(String bikeId) {
        Bike bike = bikes.get(bikeId);
        if (bike == null) {
            throw new RentalException("Bike not found: " + bikeId);
        }
        return bike;
    }
}
