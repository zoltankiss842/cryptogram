package main.exceptions;

public class NoSaveGameFound extends Exception{

    public NoSaveGameFound() {
        super();
    }

    public NoSaveGameFound(String message) {
        super(message);
    }
}
