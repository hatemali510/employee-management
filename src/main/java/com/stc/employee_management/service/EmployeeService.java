package com.stc.employee_management.service;



import com.stc.employee_management.dto.EmployeeDto;
import com.stc.employee_management.exception.InvalidInputException;
import com.stc.employee_management.exception.employee.EmployeeNotFoundException;
import com.stc.employee_management.model.Employee;
import com.stc.employee_management.repository.EmployeeRepository;
import com.stc.employee_management.validation.ThirdPartyValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class EmployeeService {
    private final EmployeeRepository repository;
    private final ThirdPartyValidator thirdPartyValidator;
    private final EmailService emailService;



    @Autowired
    public EmployeeService(EmployeeRepository repository, ThirdPartyValidator thirdPartyValidator, EmailService emailService) {
        this.repository = repository;
        this.thirdPartyValidator = thirdPartyValidator;
        this.emailService = emailService;
    }

    @Transactional
    public Employee createEmployee(EmployeeDto employeeDto) throws InvalidInputException {
        // Third-party validation
        if (Boolean.FALSE.equals(validateEmployeeInput(employeeDto))) {
            throw new InvalidInputException("Invalid email or department");
        }
        // async call
        emailService.sendWelcomeRegistrationMail(employeeDto.getEmail());
        ModelMapper modelMapper=new ModelMapper();
        Employee employee=modelMapper.map(employeeDto,Employee.class);
        return repository.save(employee);
    }

    private Boolean validateEmployeeInput(EmployeeDto employee){
        return thirdPartyValidator.isEmailValid(employee.getEmail()) &&
                thirdPartyValidator.isDepartmentValid(employee.getDepartment());
    }

    public Employee getEmployeeById(Long id) throws EmployeeNotFoundException {
        return repository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found"));
    }


    public Employee updateEmployee(Long id, EmployeeDto newEmployeeData) throws EmployeeNotFoundException {
        return repository.findById(id).map(employee -> {
            employee.setFirstName(newEmployeeData.getFirstName());
            employee.setLastName(newEmployeeData.getLastName());
            employee.setEmail(newEmployeeData.getEmail());
            employee.setDepartment(newEmployeeData.getDepartment());
            employee.setSalary(newEmployeeData.getSalary());
            return repository.save(employee);
        }).orElseThrow(() -> new EmployeeNotFoundException("Employee not found."));
    }

    public void deleteEmployee(Long id) throws EmployeeNotFoundException {
        if (!repository.existsById(id)) {
            throw new EmployeeNotFoundException("Employee not found.");
        }
        repository.deleteById(id);
    }

    public List<Employee> getAllEmployees() {
        return repository.findAll();
    }

}
