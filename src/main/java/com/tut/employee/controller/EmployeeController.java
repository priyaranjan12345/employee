package com.tut.employee.controller;

import com.tut.employee.model.Employee;
import com.tut.employee.model.EmployeeRequest;
import com.tut.employee.service.IEmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/employee")
@RequiredArgsConstructor
public class EmployeeController {
    private final IEmployeeService iEmployeeService;

    // REST APIs
    @PostMapping("/create")
    public ResponseEntity<Employee> saveEmployee(@RequestBody EmployeeRequest employeeRequest) {
        Employee emp = iEmployeeService.saveEmployee(employeeRequest);
        return new ResponseEntity<Employee>(emp, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Employee>> getAllEmployees(){
        List<Employee> allEmp = iEmployeeService.allEmployees();
        return new ResponseEntity<List<Employee>>(allEmp, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") long employeeId) {
        Employee emp = iEmployeeService.getEmployeeById(employeeId);
        return new ResponseEntity<Employee>(emp, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Employee> updateEmployee(@RequestBody Employee employee, @PathVariable("id") long id){
        Employee newEmp =  iEmployeeService.updateEmployee(employee, id);
        return new ResponseEntity<Employee>(newEmp, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEmployee(@PathVariable("id") long id) {
        iEmployeeService.deleteEmployee(id);
    }

    @DeleteMapping("/deleteAll")
    public void deleteAllEmployee() {
        iEmployeeService.deleteAll();
    }
}
