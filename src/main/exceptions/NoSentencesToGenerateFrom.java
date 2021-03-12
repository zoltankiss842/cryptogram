package main.exceptions;

/**
 * This is an exception class, if
 * there are no sentences present or the text file is empty for sentences.
 */
public class NoSentencesToGenerateFrom extends Exception {

    public NoSentencesToGenerateFrom() {
        super();
    }

    public NoSentencesToGenerateFrom(String message) {
        super(message);
    }
}
