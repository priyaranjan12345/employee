package com.tut.employee.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tut.employee.model.Employee;
import com.tut.employee.service.IEmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeControllerTest {
    @MockBean
    private IEmployeeService iEmployeeService;

    @Autowired
    private MockMvc mockMvc;

    Employee employee;

    @BeforeEach
    public void init() {
        employee = Employee.builder()
                .firstName("Priyaranjan")
                .lastName("Mantri")
                .email("priyaranjanmantri@gmail.com")
                .build();
    }

    @Test
    @DisplayName("Test Save Employee Api")
    public void saveEmployeeTest() throws Exception {
        Mockito.when(iEmployeeService.saveEmployee(Mockito.any())).thenReturn(employee);
        this.mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/employee/create").contentType(MediaType.APPLICATION_JSON)
                                .content(convertObjectToJson(employee))
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").exists());
    }

    private String convertObjectToJson(Object employee) {
        try {
            return new ObjectMapper().writeValueAsString(employee);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "{}";
        }
    }

    @Test
    @DisplayName("Test Update Employee Api")
    public void updateEmployeeTest() throws Exception {
        long empId = 10L;

        // updated employee
        employee = Employee.builder()
                .firstName("Priyaranjan")
                .lastName("Mantri")
                .email("priyaranjan@gmail.com")
                .build();

        Mockito.when(iEmployeeService.updateEmployee(Mockito.any(), Mockito.anyLong())).thenReturn(employee);

        this.mockMvc.perform(
                        MockMvcRequestBuilders.put("/api/employee/update/" + empId)
                                //.header(HttpHeaders.AUTHORIZATION, "jwt_token")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(convertObjectToJson(employee)) // body
                                .accept(MediaType.APPLICATION_JSON)

                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").exists());
    }

    @Test
    @DisplayName("Test All Employees Api")
    public void getAllEmployees() throws Exception {
        Employee employee1 = Employee.builder()
                .firstName("Priyaranjan")
                .lastName("Mantri")
                .email("johnsmith@gmail.com")
                .build();
        Employee employee2 = Employee.builder()
                .firstName("John")
                .lastName("Smith")
                .email("johnsmith@gmail.com")
                .build();
        Employee employee3 = Employee.builder()
                .firstName("Emma")
                .lastName("Hussein")
                .email("johnsmith@gmail.com")
                .build();

        List<Employee> employees = Arrays.asList(employee1, employee2, employee2);

        Mockito.when(iEmployeeService.allEmployees()).thenReturn(employees);

        this.mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/employee/all")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)

                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Test Delete Employee Api")
    public void deleteEmployee() throws Exception {
        long empId = 10L;

        // Mockito.when(iEmployeeService.deleteEmployee(Mockito.anyLong())).thenReturn();
        this.mockMvc.perform(
                        MockMvcRequestBuilders.delete("/api/employee/delete/" + empId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}
