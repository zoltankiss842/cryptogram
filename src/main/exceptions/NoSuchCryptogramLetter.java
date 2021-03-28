package main.exceptions;

/**
 * This is an exception class, if
 * the user is trying to enter a letter, that does not exist
 * in the cryptogram alphabet.
 */
public class NoSuchCryptogramLetter extends Exception{

    public NoSuchCryptogramLetter(String message) {
        super(message);
    }
}
