package main.exceptions;

/**
 * This is an exception class, if
 * there are no active games present.
 */
public class NoGameBeingPlayed extends Exception{

    public NoGameBeingPlayed() {
        super();
    }

    public NoGameBeingPlayed(String message) {
        super(message);
    }
}
