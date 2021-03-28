package main.exceptions;

/**
 * This class represents an exception, if there was problem with generating a new game.
 */
public class InvalidGameCreation extends Exception{

    public InvalidGameCreation() {
        super();
    }

    public InvalidGameCreation(String message) {
        super(message);
    }

}
