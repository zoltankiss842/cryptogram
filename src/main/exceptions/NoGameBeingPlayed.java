package main.exceptions;

public class NoGameBeingPlayed extends Exception{

    public NoGameBeingPlayed() {
        super();
    }

    public NoGameBeingPlayed(String message) {
        super(message);
    }
}
