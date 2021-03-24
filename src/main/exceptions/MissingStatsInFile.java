package main.exceptions;

public class MissingStatsInFile extends Exception {

    public MissingStatsInFile(){super();}

    public MissingStatsInFile(String message){super(message);}
}
