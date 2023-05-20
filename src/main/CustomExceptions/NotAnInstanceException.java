package main.CustomExceptions;

public class NotAnInstanceException extends Exception{
    public NotAnInstanceException(String e) {
        super(e);
    }
}
