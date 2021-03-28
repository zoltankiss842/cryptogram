package main.exceptions;

/**
 * This class represents an exception, if there was problem with reading username for the scoreboard.
 */
public class MissingNameInFile extends Exception{

    public MissingNameInFile(){super();}

    public MissingNameInFile(String message){super(message);}
}