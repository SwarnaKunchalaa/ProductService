package com.scaler.productservice.controlleradvice;

import com.scaler.productservice.dtos.ExceptionDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionalHandler {

    @ExceptionHandler(ArithmeticException.class)
    public ResponseEntity<ExceptionDto> handlerArithmeticException(){
        ExceptionDto ex = new ExceptionDto();
        ex.setMessage("ArithmeticException");
        ex.setSolution("I don't know what to do");
        ResponseEntity<ExceptionDto> response = new ResponseEntity<>(
                ex,
                HttpStatus.NOT_FOUND
        );
        return response;

    }
}
