package com.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.demo.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

	@Query(value = "select *from user where user_city=?1", nativeQuery = true)
	public List<User> findByCity(String city);
}
