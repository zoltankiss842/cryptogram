package main.exceptions;

/**
 * This class represents an exception, if there are no saved games for the current player.
 */
public class NoSaveGameFound extends Exception{

    public NoSaveGameFound() {
        super();
    }

    public NoSaveGameFound(String message) {
        super(message);
    }
}
