package com.demo.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.demo.exception.EmployeeNotFoundException;
import com.demo.exception.InvalidRequestException;
import com.demo.model.Employee;
import com.demo.repository.EmployeeRepository;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
class EmployeeServiceImplTest {

	@InjectMocks
	EmployeeServiceImpl employeeServiceImpl;

	@Mock
	EmployeeRepository employeeRepository;

	@Test
	void test_saveEmployee() {

		Employee employee = new Employee();

		employee.setId(1);
		employee.setName("Tejas");
		employee.setCity("Karanja");

		when(employeeRepository.save(employee)).thenReturn(employee);

		Employee emp = employeeServiceImpl.saveEmployee(employee);

		assertEquals(employee, emp);
	}

	@Test
	void test_getEmployeeByCity() {

		String city = "karanja";

		List<Employee> employeeList = new ArrayList<>();

		Employee employee1 = new Employee();

		employee1.setId(1);
		employee1.setName("tejas");
		employee1.setCity("karanja");

		Employee employee2 = new Employee();

		employee2.setId(1);
		employee2.setName("amar");
		employee2.setCity("karanja");

		employeeList.add(employee1);
		employeeList.add(employee2);

		when(employeeRepository.findByCity(city)).thenReturn(employeeList);

		assertEquals(employeeList, employeeServiceImpl.getEmployeeByCity(city));
	}

	@Test
	void test_GetEmployeeById() {

		Employee employee = new Employee();

		employee.setId(1);
		employee.setName("John Doe");
		employee.setCity("London");

		// Employee Found Scenario
		when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));

		Employee foundEmployee = employeeServiceImpl.getEmployeeById(1);

		assertNotNull(foundEmployee);
		assertEquals(1, foundEmployee.getId());
		assertEquals("John Doe", foundEmployee.getName());
		assertEquals("London", foundEmployee.getCity());

		// Employee Not Found Scenario
		when(employeeRepository.findById(2)).thenReturn(Optional.empty());

		Exception exception = assertThrows(EmployeeNotFoundException.class, () -> {
			employeeServiceImpl.getEmployeeById(2);
		});

		String expectedMessage = "Employee not found with id: 2";
		String actualMessage = exception.getMessage();
		assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	void test_DeleteEmployeeById() {
		// Test case for employee exists
		Integer existingEmployeeId = 1;
		when(employeeRepository.existsById(existingEmployeeId)).thenReturn(true);

		// No exception should be thrown, and deleteById should be called
		assertDoesNotThrow(() -> {
			employeeServiceImpl.deleteEmployeeById(existingEmployeeId);
		});

		verify(employeeRepository, times(1)).deleteById(existingEmployeeId);

		// Test case for employee does not exist
		Integer nonExistingEmployeeId = 2;
		when(employeeRepository.existsById(nonExistingEmployeeId)).thenReturn(false);

		// Expecting an EmployeeNotFoundException to be thrown
		Exception exception = assertThrows(EmployeeNotFoundException.class, () -> {
			employeeServiceImpl.deleteEmployeeById(nonExistingEmployeeId);
		});

		String expectedMessage = "Employee with ID " + nonExistingEmployeeId + " does not exist";
		String actualMessage = exception.getMessage();
		assertTrue(actualMessage.contains(expectedMessage));

		// Verify that deleteById was not called for non-existing employee
		verify(employeeRepository, never()).deleteById(nonExistingEmployeeId);
	}

	@Test
	public void test_UpdateEmployee() {
		// Mock data
		Employee existingEmployee = new Employee();
		existingEmployee.setId(1);
		existingEmployee.setName("Alice Smith");
		existingEmployee.setCity("London");

		Employee updatedEmployeeData = new Employee();
		updatedEmployeeData.setId(1);
		updatedEmployeeData.setName("Alice Johnson");
		updatedEmployeeData.setCity("New York");

		// Mock repository behavior
		when(employeeRepository.findById(1)).thenReturn(Optional.of(existingEmployee));
		when(employeeRepository.save(any(Employee.class))).thenReturn(updatedEmployeeData);

		// Test valid employee update
		Employee updatedEmployee = employeeServiceImpl.updateEmployee(updatedEmployeeData, 1);
		assertEquals("Alice Johnson", updatedEmployee.getName());
		assertEquals("New York", updatedEmployee.getCity());

		// Test updating with null or empty name
		Employee invalidNameEmployee = new Employee();
		invalidNameEmployee.setId(1);
		invalidNameEmployee.setCity("New Delhi");
		assertThrows(InvalidRequestException.class, () -> {
			employeeServiceImpl.updateEmployee(invalidNameEmployee, 1);
		});

		// Test updating with null or empty city
		Employee invalidCityEmployee = new Employee();
		invalidCityEmployee.setId(1);
		invalidCityEmployee.setName("Bob");
		assertThrows(InvalidRequestException.class, () -> {
			employeeServiceImpl.updateEmployee(invalidCityEmployee, 1);
		});

		// Test updating non-existing employee
		when(employeeRepository.findById(2)).thenReturn(Optional.empty());
		assertThrows(EmployeeNotFoundException.class, () -> {
			employeeServiceImpl.updateEmployee(updatedEmployeeData, 2);
		});
	}
}
