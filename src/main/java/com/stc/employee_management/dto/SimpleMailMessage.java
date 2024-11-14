package com.stc.employee_management.dto;


import lombok.Data;

@Data
public class SimpleMailMessage {

    private String to;
    private String subject;
    private String text;
    private String from;

}
