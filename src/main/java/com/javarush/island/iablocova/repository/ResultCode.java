package com.javarush.island.iablocova.repository;

public enum ResultCode {
    OK("День прошёл успешно.\n"),
    ERROR("День завершился с ошибкой!\n");

    private final String message;

    ResultCode(String message)
    {
        this.message = message;
    }

    public String getResultMessage()
    {
        return message;
    }
}
