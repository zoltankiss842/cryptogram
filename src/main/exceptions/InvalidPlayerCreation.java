package main.exceptions;

/**
 * This class represents an exception, if there was problem with loading an existing player.
 */
public class InvalidPlayerCreation extends Exception{

    public InvalidPlayerCreation() {
        super();
    }

    public InvalidPlayerCreation(String message) {
        super(message);
    }

}
