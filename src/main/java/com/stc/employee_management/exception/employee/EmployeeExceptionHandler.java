package com.stc.employee_management.exception.employee;


import com.stc.employee_management.exception.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class EmployeeExceptionHandler {

    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<ErrorCode> handleEmployeeNotFound(EmployeeNotFoundException ex) {
        ErrorCode errorCode=new ErrorCode(HttpStatus.NOT_FOUND.toString(),ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorCode);
    }

}
