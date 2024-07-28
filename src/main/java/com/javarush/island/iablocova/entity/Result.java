package com.javarush.island.iablocova.entity;

import com.javarush.island.iablocova.repository.ResultCode;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class Result {

    private final ResultCode resultCode;

    private Exception exception;
    private String dayLength;
    private String dayLoadTime;
    private String statisticsTime;

    public Result(ResultCode resultCode)
    {
        this.resultCode = resultCode;
    }

    public Result(ResultCode resultCode, Exception exception)
    {
        this.resultCode = resultCode;
        this.exception = exception;
    }

    public Result(ResultCode resultCode, Exception exception, String dayLength)
    {
        this.resultCode = resultCode;
        this.exception = exception;
        this.dayLength = dayLength;
    }

    public Result(ResultCode resultCode, String dayLength, String dayLoadTime, String statisticsTime)
    {
        this.resultCode = resultCode;
        this.dayLength = dayLength;
        this.dayLoadTime = dayLoadTime;
        this.statisticsTime = statisticsTime;
    }
}
