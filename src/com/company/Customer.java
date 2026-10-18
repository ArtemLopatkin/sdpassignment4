package com.company;

public class Customer {
    private final String id;
    private final String name;
    private final int age;
    private final boolean blocked;

    public Customer(String id, String name, int age, boolean blocked) {
        if (id == null || id.trim().isEmpty() || name == null || name.trim().isEmpty()) {
            throw new RentalException("Customer ID and name are required.");
        }
        if (age < 0) {
            throw new RentalException("Age cannot be negative.");
        }
        this.id = id;
        this.name = name;
        this.age = age;
        this.blocked = blocked;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean canRent() {
        return age >= 18 && !blocked;
    }
}
