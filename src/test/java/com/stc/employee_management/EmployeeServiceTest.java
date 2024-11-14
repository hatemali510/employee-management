package com.stc.employee_management;

import com.stc.employee_management.dto.EmployeeDto;
import com.stc.employee_management.exception.InvalidInputException;
import com.stc.employee_management.exception.employee.EmployeeNotFoundException;
import com.stc.employee_management.model.Employee;
import com.stc.employee_management.repository.EmployeeRepository;
import com.stc.employee_management.service.EmailService;
import com.stc.employee_management.service.EmployeeService;
import com.stc.employee_management.validation.ThirdPartyValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmployeeServiceTest {

    @Mock
    private EmployeeRepository repository;

    @Mock
    private ThirdPartyValidator thirdPartyValidator;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private EmployeeService employeeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateEmployee_ValidInput_ShouldReturnEmployee() throws InvalidInputException {
        EmployeeDto employeeDto = new EmployeeDto(null,"John", "Doe", "john.doe@example.com", "IT", 50000.0);
        Employee employee = new Employee();
        employee.setSalary(50000.0);
        employee.setDepartment("IT");
        employee.setEmail("john.doe@example.com");
        employee.setLastName("Doe");
        employee.setFirstName("John");
        when(thirdPartyValidator.isEmailValid(ArgumentMatchers.anyString())).thenReturn(true);
        when(thirdPartyValidator.isDepartmentValid(ArgumentMatchers.anyString())).thenReturn(true);
        when(repository.save(ArgumentMatchers.any())).thenReturn(employee);
        
        Employee result = employeeService.createEmployee(employeeDto);
        
        assertNotNull(result);
        verify(emailService, times(1)).sendWelcomeRegistrationMail(employeeDto.getEmail());
        verify(repository, times(1)).save(employee);
    }

    @Test
    void testCreateEmployee_InvalidInput_ShouldThrowInvalidInputException() {
        EmployeeDto employeeDto = new EmployeeDto(null,"John", "Doe", "invalid-email", "Unknown", 50000.0);
        
        when(thirdPartyValidator.isEmailValid(employeeDto.getEmail())).thenReturn(false);
        
        assertThrows(InvalidInputException.class, () -> employeeService.createEmployee(employeeDto));
    }

    @Test
    void testGetEmployeeById_ExistingId_ShouldReturnEmployee() throws EmployeeNotFoundException {
        Long id = 1L;
        Employee employee = new Employee();
        employee.setId(1L);
        when(repository.findById(id)).thenReturn(Optional.of(employee));
        
        Employee result = employeeService.getEmployeeById(id);
        
        assertEquals(employee, result);
    }

    @Test
    void testGetEmployeeById_NonExistingId_ShouldThrowEmployeeNotFoundException() {
        Long id = 1L;
        
        when(repository.findById(id)).thenReturn(Optional.empty());
        
        assertThrows(EmployeeNotFoundException.class, () -> employeeService.getEmployeeById(id));
    }

    @Test
    void testUpdateEmployee_ExistingId_ShouldReturnUpdatedEmployee() throws EmployeeNotFoundException {
        Long id = 1L;
        EmployeeDto newEmployeeData = new EmployeeDto(null,"Jane", "Doe", "jane.doe@example.com", "HR", 50000.0);
        Employee employee = new Employee();
        employee.setId(1L);
        when(repository.findById(id)).thenReturn(Optional.of(employee));
        when(repository.save(employee)).thenReturn(employee);
        
        Employee result = employeeService.updateEmployee(id, newEmployeeData);
        
        assertEquals(newEmployeeData.getEmail(), employee.getEmail());
        verify(repository, times(1)).save(employee);
    }

    @Test
    void testUpdateEmployee_NonExistingId_ShouldThrowEmployeeNotFoundException() {
        Long id = 1L;
        EmployeeDto newEmployeeData = new EmployeeDto(null,"Jane", "Doe", "jane.doe@example.com", "HR", 50000.0);
        
        when(repository.findById(id)).thenReturn(Optional.empty());
        
        assertThrows(EmployeeNotFoundException.class, () -> employeeService.updateEmployee(id, newEmployeeData));
    }

    @Test
    void testDeleteEmployee_ExistingId_ShouldDeleteEmployee() throws EmployeeNotFoundException {
        Long id = 1L;
        
        when(repository.existsById(id)).thenReturn(true);
        
        employeeService.deleteEmployee(id);
        
        verify(repository, times(1)).deleteById(id);
    }

    @Test
    void testDeleteEmployee_NonExistingId_ShouldThrowEmployeeNotFoundException() {
        Long id = 1L;
        
        when(repository.existsById(id)).thenReturn(false);
        
        assertThrows(EmployeeNotFoundException.class, () -> employeeService.deleteEmployee(id));
    }
}
