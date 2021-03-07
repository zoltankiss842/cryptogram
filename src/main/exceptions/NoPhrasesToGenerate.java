package main.exceptions;

public class NoPhrasesToGenerate extends Exception {

    public NoPhrasesToGenerate() {
        super();
    }

    public NoPhrasesToGenerate(String message) {
        super(message);
    }
}
