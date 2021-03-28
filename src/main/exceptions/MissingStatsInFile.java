package main.exceptions;

/**
 * This class represents an exception, if there was problem with reading stats for the scoreboard.
 */
public class MissingStatsInFile extends Exception {

    public MissingStatsInFile(String message){super(message);}
}
