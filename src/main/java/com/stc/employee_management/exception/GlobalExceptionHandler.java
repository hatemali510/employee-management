package com.stc.employee_management.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;



@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorCode> handleException(Exception ex) {
        ErrorCode errorCode=new ErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.toString(),ex.getMessage());
        return ResponseEntity.internalServerError().body(errorCode);
    }

    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<ErrorCode> handleInvalidInput(InvalidInputException ex) {
        ErrorCode errorCode=new ErrorCode(HttpStatus.BAD_REQUEST.toString(),ex.getMessage());
        return ResponseEntity.badRequest().body(errorCode);
    }


}