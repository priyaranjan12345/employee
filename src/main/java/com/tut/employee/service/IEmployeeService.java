package com.tut.employee.service;

import com.tut.employee.model.Employee;
import com.tut.employee.model.EmployeeRequest;

import java.util.List;

public interface IEmployeeService {
	Employee saveEmployee(EmployeeRequest employeeRequest);
	List<Employee> allEmployees();
	Employee getEmployeeById(long id);
	Employee updateEmployee(Employee employee, long id);
	void deleteEmployee(long id);
	void deleteAll();
}
