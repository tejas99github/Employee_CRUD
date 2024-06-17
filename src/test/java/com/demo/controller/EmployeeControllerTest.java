package com.demo.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.demo.model.Employee;
import com.demo.service.EmployeeService;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
class EmployeeControllerTest {

	@Autowired
	MockMvc mockMvc;

	@InjectMocks
	EmployeeController employeeController;

	@Mock
	EmployeeService employeeService;

//	@Autowired
//	ObjectMapper objectMapper; // Jackson ObjectMapper for JSON conversion

//	@Test
//	void testSaveEmployee() throws Exception {
//		// Create a sample employee object
//		Employee employee = new Employee();
//		employee.setId(1);
//		employee.setName("tejas");
//		employee.setCity("karanja");
//
//		// Convert employee object to JSON
//		String employeeString = objectMapper.writeValueAsString(employee);
//
//		// Mock the behavior of the service layer
//		when(employeeService.saveEmployee(employee)).thenReturn(employee);
//
//		// Perform the POST request and verify status
//		mockMvc.perform(post("/employee/saveEmp").contentType(MediaType.APPLICATION_JSON).content(employeeString))
//				.andExpect(status().isOk());
//	}

	@Test
	public void testSaveEmployee() {
		// Given
		Employee employeeToSave = new Employee();
		employeeToSave.setName("John Doe");
		employeeToSave.setCity("London");
		// You may set other properties as needed

		Employee savedEmployee = new Employee();
		savedEmployee.setId(1); // Assuming the ID generated after saving
		savedEmployee.setName("John Doe");
		savedEmployee.setCity("London");
		// You may set other properties as needed

		// Mocking behavior for saveEmployee method
		when(employeeService.saveEmployee(any(Employee.class))).thenReturn(savedEmployee);

		// When
		ResponseEntity<Employee> response = employeeController.saveEmployee(employeeToSave);

		// Then
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals(savedEmployee.getId(), response.getBody().getId());
		assertEquals(savedEmployee.getName(), response.getBody().getName());
		assertEquals(savedEmployee.getCity(), response.getBody().getCity());
	}

	@Test
	public void testGetEmployeeById() {
		// Mocking behavior for employeeService.getEmployeeById(id) method
		// Mocking directly inside the test method
		Employee mockEmployee = new Employee();
		mockEmployee.setId(1);
		mockEmployee.setName("John Doe");
		mockEmployee.setCity("London");
		when(employeeService.getEmployeeById(1)).thenReturn(mockEmployee);

		// Test case for getting employee by ID
		int employeeId = 1;

		// When
		ResponseEntity<Employee> response = employeeController.getEmployeeById(employeeId);

		// Then
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals(employeeId, response.getBody().getId());
		// Add more assertions based on your expected behavior
	}

	@Test
	public void testDeleteEmployeeById() {
		// Given
		int employeeId = 1;

		// Mocking behavior for deleteEmployeeById method
		doNothing().when(employeeService).deleteEmployeeById(employeeId);

		// When
		String result = employeeController.deleteEmployeeById(employeeId);

		// Then
		verify(employeeService).deleteEmployeeById(employeeId);
		assertEquals("Employee data deleted successfully !", result);
	}

	@Test
	public void testUpdateEmployeeById() {
		// Given
		int employeeId = 1;
		Employee updatedEmployee = new Employee();
		updatedEmployee.setId(1);
		updatedEmployee.setName("John Doe");
		updatedEmployee.setCity("London");

		// Mocking behavior for updateEmployee method
		when(employeeService.updateEmployee(any(Employee.class), any(Integer.class))).thenReturn(updatedEmployee);

		// When
		ResponseEntity<Employee> response = employeeController.updateEmployeeById(updatedEmployee, employeeId);

		// Then
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals(updatedEmployee.getId(), response.getBody().getId());
		assertEquals(updatedEmployee.getName(), response.getBody().getName());
		assertEquals(updatedEmployee.getCity(), response.getBody().getCity());
	}

	@Test
	public void testGetEmployeeByCity() {
		// Given
		String city = "London";
		Employee employee1 = new Employee();
		employee1.setId(1);
		employee1.setName("John Doe");
		employee1.setCity("London");

		Employee employee2 = new Employee();
		employee2.setId(2);
		employee2.setName("Jane Smith");
		employee2.setCity("London");

		List<Employee> employeesInCity = Arrays.asList(employee1, employee2);

		// Mocking behavior for getEmployeeByCity method
		when(employeeService.getEmployeeByCity(anyString())).thenReturn(employeesInCity);

		// When
		ResponseEntity<List<Employee>> response = employeeController.getEmployeeByCity(city);

		// Then
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals(2, response.getBody().size()); // Assuming 2 employees in the city
	}
}
