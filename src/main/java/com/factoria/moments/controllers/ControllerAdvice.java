package com.factoria.moments.controllers;

import com.factoria.moments.dtos.error.ErrorDto;
import com.factoria.moments.exceptions.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<ErrorDto> notFoundExceptionHandler(NotFoundException exception){
        var error = ErrorDto.builder()
                .code(exception.getCode())
                .message(exception.getMessage())
                .build();
        return new ResponseEntity<>(error, exception.getHttpStatus());
    }
}
