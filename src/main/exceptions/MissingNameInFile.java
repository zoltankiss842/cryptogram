package main.exceptions;

public class MissingNameInFile extends Exception{

    public MissingNameInFile(){super();}

    public MissingNameInFile(String message){super(message);}
}