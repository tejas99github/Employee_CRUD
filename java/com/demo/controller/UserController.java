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

import com.demo.model.User;
import com.demo.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/save")
	public ResponseEntity<User> saveUser(@RequestBody User user) {
		User use = userService.saveUser(user);
		return ResponseEntity.ok().body(use);
	}

	@GetMapping("/getUser/{id}")
	public ResponseEntity<User> getUserById(@PathVariable Integer id) {
		User user = userService.getUserById(id);
		return ResponseEntity.ok().body(user);
	}

	@DeleteMapping("/delete/{id}")
	public void deleteUserById(@PathVariable Integer id) {
		userService.deleteUserById(id);
	}

	@PutMapping("/update/{id}")
	public User updateUserById(@RequestBody User user, @PathVariable Integer id) {
		User updatedUser = userService.updateUserById(user, id);
		return updatedUser;
	}

	@GetMapping("/getUserByCity/{city}")
	public ResponseEntity<List<User>> getUserByCity(@PathVariable("city") String city) {
		List<User> userList = userService.getListByCity(city);
		return ResponseEntity.ok().body(userList);
	}

}
