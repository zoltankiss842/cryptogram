package main.exceptions;

public class NoSuchGameType extends Exception {

    public NoSuchGameType() {
        super();
    }

    public NoSuchGameType(String message) {
        super(message);
    }
}
