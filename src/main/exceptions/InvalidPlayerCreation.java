package main.exceptions;

public class InvalidPlayerCreation extends Exception{

    public InvalidPlayerCreation() {
        super();
    }

    public InvalidPlayerCreation(String message) {
        super(message);
    }

}
