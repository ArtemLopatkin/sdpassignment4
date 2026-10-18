package com.company;

public class RentalException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public RentalException(String message) {
        super(message);
    }

    public RentalException(String message, Throwable cause) {
        super(message, cause);
    }
}
