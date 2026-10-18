package com.company;

import java.math.BigDecimal;

public class RentalFacade {
    private final CustomerService customerService = new CustomerService();
    private final BikeStorage bikeStorage = new BikeStorage();
    private final RentalPricing rentalPricing = new RentalPricing();
    private final PaymentService paymentService = new PaymentService();
    private final RentalService rentalService = new RentalService();

    public RentalFacade() {
        customerService.addCustomer(new Customer("C1", "Artem", 21, false));
        customerService.addCustomer(new Customer("C2", "Dana", 23, false));
        customerService.addCustomer(new Customer("C3", "Miras", 17, false));
        customerService.addCustomer(new Customer("C4", "Amina", 25, true));

        paymentService.addAccount("C1", new BigDecimal("10000.00"));
        paymentService.addAccount("C2", new BigDecimal("300.00"));
        paymentService.addAccount("C3", new BigDecimal("5000.00"));
        paymentService.addAccount("C4", new BigDecimal("5000.00"));

        bikeStorage.addBike(new Bike("B1", "City bike", new BigDecimal("700.00")));
        bikeStorage.addBike(new Bike("B2", "Mountain bike", new BigDecimal("1000.00")));
        bikeStorage.addBike(new Bike("B3", "Electric bike", new BigDecimal("1500.00")));
    }

    public String listAvailableBikes() {
        return bikeStorage.listAvailableBikes();
    }

    public String rentBike(String customerId, String bikeId, int hours) {
        Customer customer = customerService.checkEligibility(customerId);
        rentalService.checkNoActiveRental(customerId);
        Bike bike = bikeStorage.findAvailableBike(bikeId);
        BigDecimal price = rentalPricing.calculatePrice(bike, hours);

        bikeStorage.reserveBike(bikeId);
        String paymentId = null;
        try {
            paymentId = paymentService.charge(customerId, price);
            Rental rental = rentalService.createRental(customer, bike, hours, price, paymentId);
            return rental.getId();
        } catch (RuntimeException exception) {
            if (paymentId != null) {
                paymentService.refund(paymentId);
            }
            bikeStorage.releaseBike(bikeId);
            throw new RentalException("Rental could not be started: " + exception.getMessage(), exception);
        }
    }

    public String getRentalInfo(String rentalId) {
        return rentalService.findRental(rentalId).describe();
    }

    public void returnBike(String rentalId) {
        Rental rental = rentalService.findActiveRental(rentalId);
        bikeStorage.releaseBike(rental.getBikeId());
        rentalService.closeRental(rentalId);
    }
}
