package main.exceptions;

/**
 * This is an exception class, if
 * the user is trying to type a letter, that is not present in the alphabet.
 */
public class NoSuchPlainLetter extends Exception{

    public NoSuchPlainLetter() {
        super();
    }

    public NoSuchPlainLetter(String message) {
        super(message);
    }
}
