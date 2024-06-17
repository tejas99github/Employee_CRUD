package com.demo.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.demo.model.Employee;

@DataJpaTest
@ActiveProfiles("test") // Activate the 'test' profile
public class EmployeeRepositoryTest {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Test
	public void whenFindByCity_thenReturnEmployees() {
		// given
		Employee emp1 = new Employee();
		emp1.setId(1);
		emp1.setName("Doe");
		emp1.setCity("New York");

		Employee emp2 = new Employee();

		emp2.setId(2);
		emp2.setName("Messy");
		emp2.setCity("New York");

		Employee emp3 = new Employee();
		emp3.setId(3);
		emp3.setName("Jersy");
		emp3.setCity("Los Angelis");

		employeeRepository.save(emp1);
		employeeRepository.save(emp2);
		employeeRepository.save(emp3);

		// when
		List<Employee> found = employeeRepository.findByCity("New York");

		// then
		assertEquals(2, found.size(), "Number of employees with city 'New York' should be 2");

		boolean doeFound = false;
		boolean messyFound = false;
		for (Employee employee : found) {
			if ("Doe".equals(employee.getName())) {
				doeFound = true;
			} else if ("Messy".equals(employee.getName())) {
				messyFound = true;
			}
		}
		assertTrue(doeFound, "John should be found");
		assertTrue(messyFound, "Jane should be found");
	}
}
