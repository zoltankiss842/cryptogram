package main.exceptions;

public class PlainLetterAlreadyInUse extends Exception{

    public PlainLetterAlreadyInUse() {
        super();
    }

    public PlainLetterAlreadyInUse(String message) {
        super(message);
    }


}
