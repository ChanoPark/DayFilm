package com.rabbit.dayfilm.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomException extends CommonExceptionAbs{
    private String element;
}
