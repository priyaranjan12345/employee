package com.tut.employee.service;

import com.tut.employee.model.Employee;
import com.tut.employee.model.EmployeeRequest;
import com.tut.employee.repository.EmployeeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class EmployeeServiceTest {
    @MockBean
    private EmployeeRepository employeeRepository;

    @Autowired
    private IEmployeeService employeeService;

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
    @DisplayName("Test Create Employee")
    public void createEmployeeTest() {
        Mockito.when(employeeRepository.save(Mockito.any())).thenReturn(employee);

        EmployeeRequest employeeRequest = EmployeeRequest.builder()
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .email(employee.getEmail())
                .build();

        Employee emp = employeeService.saveEmployee(employeeRequest);

        System.out.println(emp.getEmail());

        Assertions.assertNotNull(emp);
        Assertions.assertEquals("Priyaranjan", emp.getFirstName());
    }

    @Test
    @DisplayName("Test Update Employee")
    public void updateEmployeeTest() {
        long empId = 10L;

        Employee emp = Employee.builder()
                .firstName("John")
                .lastName("Smith")
                .email("johnsmith@gmail.com")
                .build();

        Mockito.when(employeeRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(emp));
        Mockito.when(employeeRepository.save(Mockito.any())).thenReturn(emp);

        Employee updatedEmployee = employeeService.updateEmployee(emp, empId);
        System.out.println(updatedEmployee.getEmail());

        Assertions.assertNotNull(emp);
        Assertions.assertEquals("John", updatedEmployee.getFirstName());
    }

    @Test
    @DisplayName("Test Delete Employee")
    public void deleteUserTest() {
        long empId = 10L;

        Employee emp = Employee.builder()
                .firstName("John")
                .lastName("Smith")
                .email("johnsmith@gmail.com")
                .build();

        Mockito.when(employeeRepository.findById(10L)).thenReturn(Optional.of(emp));
        employeeService.deleteEmployee(empId);

        Mockito.verify(employeeRepository, Mockito.times(1)).deleteById(empId);
    }

    @Test
    @DisplayName("Test Get Employee")
    public void getEmployeeTest() {
        long empId = 10L;

        Employee emp = Employee.builder()
                .firstName("John")
                .lastName("Smith")
                .email("johnsmith@gmail.com")
                .build();

        Mockito.when(employeeRepository.findById(10L)).thenReturn(Optional.of(emp));

        Employee employee = employeeService.getEmployeeById(empId);

        Assertions.assertNotNull(employee);
        Assertions.assertEquals(emp.getFirstName(), employee.getFirstName());
//        Assertions.assertThrows(RuntimeException.class, () -> {
//            throw new ResourceNotFoundException("Employee", "ID", empId);
//        });
    }

    @Test
    @DisplayName("Test Get All Employees")
    public void getAllEmployeeTest() {
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

        Mockito.when(employeeRepository.findAll()).thenReturn(employees);

        List<Employee> data = employeeService.allEmployees();
        Assertions.assertIterableEquals(data, employees);
    }

}
