package main.exceptions;

public class NoSuchCryptogramLetter extends Exception{

    public NoSuchCryptogramLetter() {
        super();
    }

    public NoSuchCryptogramLetter(String message) {
        super(message);
    }
}
