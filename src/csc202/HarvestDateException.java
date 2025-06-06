package csc202;

public class HarvestDateException extends RuntimeException {

    HarvestDateException() {
        super("Harvest date can't be in the past.");
    }
}