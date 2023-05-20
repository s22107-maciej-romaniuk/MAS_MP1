package main.CustomExceptions;

public class UnfitForServiceException extends Exception{
    public UnfitForServiceException(String message){
        super(message);
    }
}
