package com.company;

public class Main {
    public static void main(String[] args) {
        RentalFacade rentalFacade = new RentalFacade();

        System.out.println("Available bikes:");
        System.out.println(rentalFacade.listAvailableBikes());

        System.out.println("\nRenting a city bike for 3 hours:");
        String rentalId = rentalFacade.rentBike("C1", "B1", 3);
        System.out.println(rentalFacade.getRentalInfo(rentalId));

        System.out.println("\nTrying to rent an occupied bike:");
        try {
            rentalFacade.rentBike("C2", "B1", 1);
        } catch (RentalException exception) {
            System.out.println(exception.getMessage());
        }

        System.out.println("\nTrying to rent with insufficient funds:");
        try {
            rentalFacade.rentBike("C2", "B2", 2);
        } catch (RentalException exception) {
            System.out.println(exception.getMessage());
        }

        System.out.println("\nAvailable bikes after the failed payment:");
        System.out.println(rentalFacade.listAvailableBikes());

        System.out.println("\nReturning the city bike:");
        rentalFacade.returnBike(rentalId);
        System.out.println(rentalFacade.getRentalInfo(rentalId));

        System.out.println("\nTrying to return the same bike again:");
        try {
            rentalFacade.returnBike(rentalId);
        } catch (RentalException exception) {
            System.out.println(exception.getMessage());
        }

        System.out.println("\nAvailable bikes:");
        System.out.println(rentalFacade.listAvailableBikes());
    }
}
