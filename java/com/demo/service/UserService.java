package com.demo.service;

import java.util.List;

import com.demo.model.User;

public interface UserService {

	public User saveUser(User user);

	public User getUserById(Integer id);

	public void deleteUserById(Integer id);

	public User updateUserById(User user, Integer id);

	public List<User> getListByCity(String city);
}
