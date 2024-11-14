package com.stc.employee_management.exception;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorCode {

    private String code;
    private String message;
}
