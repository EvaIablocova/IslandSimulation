package com.javarush.island.iablocova.exception;

public class ParametersException extends RuntimeException{
    public ParametersException()
    {
    }

    public ParametersException(String message)
    {
        super(message);
    }

    public ParametersException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public ParametersException(Throwable cause)
    {
        super(cause);
    }
}
