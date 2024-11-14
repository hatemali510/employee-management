package com.stc.employee_management;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stc.employee_management.dto.EmployeeDto;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
@AutoConfigureMockMvc
class EmployeeControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateEmployee() throws Exception {
        EmployeeDto employeeDto=new EmployeeDto(null,"test","test","test@test.com","IT",50.0);

        mockMvc.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employeeDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value("test"))
                .andExpect(jsonPath("$.department").value("IT"));
    }

    @Nested
    class GetEmployeeControllerTest {
        @Test
        void testGetEmployeeById() throws Exception {
            Long employeeId = 1L; // Assume an employee with this ID exists in the test database

            mockMvc.perform(get("/api/employees/{id}", employeeId))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(employeeId));
        }

        @Nested
        class UpdateEmployeeControllerTest{
            @Test
            void testUpdateEmployee() throws Exception {
                Long employeeId = 1L; // Assume an employee with this ID exists in the test database
                EmployeeDto employeeDto=new EmployeeDto(null,"test","test","test@test.com","engineering",100.0);


                mockMvc.perform(put("/api/employees/{id}", employeeId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(employeeDto)))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.department").value("engineering"))
                        .andExpect(jsonPath("$.salary").value(100.0));
            }

            @Nested
            class GetAllEmployeeControllerTest{
                @Test
                void testGetAllEmployees() throws Exception {
                    mockMvc.perform(get("/api/employees"))
                            .andExpect(status().isOk())
                            .andExpect(jsonPath("$").isArray());
                }

                @Nested
                class DeleteEmployeeControllerTest{
                    @Test
                    void testDeleteEmployee() throws Exception {
                        Long employeeId = 1L; // Assume an employee with this ID exists in the test database

                        mockMvc.perform(delete("/api/employees/{id}", employeeId))
                                .andExpect(status().isNoContent());
                    }
                }
            }
        }
    }






}
