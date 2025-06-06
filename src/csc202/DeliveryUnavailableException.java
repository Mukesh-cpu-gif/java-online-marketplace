package csc202;

public class DeliveryUnavailableException extends RuntimeException {

    DeliveryUnavailableException() {
        super("Address is out of range.");
    }
}