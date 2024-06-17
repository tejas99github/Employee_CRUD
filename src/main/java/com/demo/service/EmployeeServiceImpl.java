package com.demo.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.exception.EmployeeNotFoundException;
import com.demo.exception.InvalidRequestException;
import com.demo.model.Employee;
import com.demo.repository.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeServiceImpl.class);

	@Override
	public Employee saveEmployee(Employee employee) {
		Employee emp = employeeRepository.save(employee);
		return emp;
	}

	@Override
	public Employee getEmployeeById(Integer id) {
		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id: " + id));
		return employee;
	}

	@Override
	public void deleteEmployeeById(Integer id) {

		if (!employeeRepository.existsById(id)) {
			throw new EmployeeNotFoundException("Employee with ID " + id + " does not exist");
		}

		employeeRepository.deleteById(id);

	}

	@Override
	public Employee updateEmployee(Employee emp, Integer id) {

		if (emp.getName() == null || emp.getName().isEmpty()) {
			LOGGER.info("Employee name is null or empty");
			throw new InvalidRequestException("Employee name must not be null or empty");
		}

		if (emp.getCity() == null || emp.getCity().isEmpty()) {
			LOGGER.info("Employee city is null or empty");
			throw new InvalidRequestException("Employee city must not be null or empty");
		}

		LOGGER.info("Updating employee with ID: " + id);

		Optional<Employee> optionalEmployee = employeeRepository.findById(id);

		if (!optionalEmployee.isPresent()) {
			LOGGER.info("Employee with ID " + id + " not found");
			throw new EmployeeNotFoundException("Employee with ID " + id + " does not exist");
		}

		Employee employee = optionalEmployee.get();

		employee.setName(emp.getName());
		employee.setCity(emp.getCity());

		Employee updatedEmployee = employeeRepository.save(employee);

		LOGGER.info("Updated employee with ID: " + id);

		return updatedEmployee;
	}

	@Override
	public List<Employee> getEmployeeByCity(String city) {
		List<Employee> employeeList = employeeRepository.findByCity(city);
		return employeeList;
	}

}
