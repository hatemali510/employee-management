package com.stc.employee_management.validation;

import com.stc.employee_management.helper.InputValidationHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ThirdPartyValidator {

    @Autowired
    private CircuitBreakerFactory circuitBreakerFactory;

    @Autowired
    RestTemplate restTemplate;

    @Value("${mail.validation.mock.api}")
    private String mailValidationMockApiUrl;

    @Value("${department.validation.mock.api}")
    private String departmentValidationMockApiUrl;

    public boolean isEmailValid(String email) {

        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("mailValidationCR");
        return circuitBreaker.run(() -> restTemplate.getForObject(mailValidationMockApiUrl, Boolean.class),
                throwable -> InputValidationHelper.isValidEmail.test(email));
    }

    public boolean isDepartmentValid(String department) {
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("departmentValidationCR");
        return circuitBreaker.run(() -> restTemplate.getForObject(departmentValidationMockApiUrl, Boolean.class),
                throwable -> InputValidationHelper.isValidDepartment.test(department));
    }
}
