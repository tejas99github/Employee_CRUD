package com.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.demo.model.Employee;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Integer> {

	@Query(value = "select * from Employee e where City=?1", nativeQuery = true)
	public List<Employee> findByCity(String city);

}
