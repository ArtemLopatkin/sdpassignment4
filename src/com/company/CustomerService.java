package com.company;

import java.util.LinkedHashMap;
import java.util.Map;

public class CustomerService {
    private final Map<String, Customer> customers = new LinkedHashMap<>();

    public void addCustomer(Customer customer) {
        if (customer == null) {
            throw new RentalException("Customer is required.");
        }
        if (customers.putIfAbsent(customer.getId(), customer) != null) {
            throw new RentalException("Customer already exists: " + customer.getId());
        }
    }

    public Customer findCustomer(String customerId) {
        Customer customer = customers.get(customerId);
        if (customer == null) {
            throw new RentalException("Customer not found: " + customerId);
        }
        return customer;
    }

    public Customer checkEligibility(String customerId) {
        Customer customer = findCustomer(customerId);
        if (!customer.canRent()) {
            throw new RentalException("Customer must be at least 18 and have an unblocked account.");
        }
        return customer;
    }
}
