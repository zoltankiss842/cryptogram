package main.exceptions;

public class NoSuchPlainLetter extends Exception{

    public NoSuchPlainLetter() {
        super();
    }

    public NoSuchPlainLetter(String message) {
        super(message);
    }
}
