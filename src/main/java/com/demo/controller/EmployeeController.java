package com.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.model.Employee;
import com.demo.service.EmployeeService;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	// Design the method which save employee data
	@PostMapping("/saveEmp")
	public ResponseEntity<Employee> saveEmployee(@RequestBody Employee employee) {
		Employee emp = employeeService.saveEmployee(employee);
		return ResponseEntity.ok().body(emp);
	}

	// Design the method which get the employee data
	@GetMapping("/getEmpById/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable Integer id) {
		Employee employeeById = employeeService.getEmployeeById(id);
		return ResponseEntity.ok().body(employeeById);
	}

	// Design the method which delete the employee data.
	@DeleteMapping("/deleteEmpById/{id}")
	public String deleteEmployeeById(@PathVariable Integer id) {
		employeeService.deleteEmployeeById(id);
		return "Employee data deleted successfully !";
	}

	// Design the method which update the employee data
	@PutMapping("/updateEmpById/{id}")
	public ResponseEntity<Employee> updateEmployeeById(@RequestBody Employee emp, @PathVariable Integer id) {
		Employee employee = employeeService.updateEmployee(emp, id);
		return ResponseEntity.ok().body(employee);
	}

	// Design the method which return list of employee by city
	@GetMapping("getEmpByCity/{city}")
	public ResponseEntity<List<Employee>> getEmployeeByCity(@PathVariable String city) {
		List<Employee> employeeList = employeeService.getEmployeeByCity(city);
		return ResponseEntity.ok().body(employeeList);
	}
}
