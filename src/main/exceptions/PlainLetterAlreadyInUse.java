package main.exceptions;

/**
 * This is an exception class, if
 * the user is trying to type a letter, that they already entered
 * in the game.
 */
public class PlainLetterAlreadyInUse extends Exception{

    public PlainLetterAlreadyInUse() {
        super();
    }

    public PlainLetterAlreadyInUse(String message) {
        super(message);
    }


}
