package main.exceptions;

public class InvalidGameCreation extends Exception{

    public InvalidGameCreation() {
        super();
    }

    public InvalidGameCreation(String message) {
        super(message);
    }

}
