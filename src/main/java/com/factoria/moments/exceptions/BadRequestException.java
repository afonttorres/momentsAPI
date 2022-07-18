package com.factoria.moments.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class BadRequestException extends RuntimeException{
    private String code;
    private HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

    public BadRequestException(String message, String code){
        super(message);
        this.code = code;
    }
}
