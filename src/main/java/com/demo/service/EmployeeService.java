package com.demo.service;

import java.util.List;

import com.demo.model.Employee;

public interface EmployeeService {

	public Employee saveEmployee(Employee employee);

	public Employee getEmployeeById(Integer id);

	public void deleteEmployeeById(Integer id);

	public Employee updateEmployee(Employee emp, Integer id);
	
	public List<Employee> getEmployeeByCity(String city);

}
