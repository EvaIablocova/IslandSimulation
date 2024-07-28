package com.javarush.island.iablocova.exception;

public class CreateObjectException extends RuntimeException{
    public CreateObjectException()
    {
    }

    public CreateObjectException(String message)
    {
        super(message);
    }

    public CreateObjectException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public CreateObjectException(Throwable cause)
    {
        super(cause);
    }
}
