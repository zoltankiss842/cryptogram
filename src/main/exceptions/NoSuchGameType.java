package main.exceptions;

/**
 * This is an exception class, if
 * the user is trying to play a non-existing game mode.
 */
public class NoSuchGameType extends Exception {

    public NoSuchGameType() {
        super();
    }

    public NoSuchGameType(String message) {
        super(message);
    }
}
