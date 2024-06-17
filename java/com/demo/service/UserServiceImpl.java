package com.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.Exception.BadRequestException;
import com.demo.Exception.ResourceNotFoundException;
import com.demo.model.User;
import com.demo.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public User saveUser(User user) {
		User use = userRepository.save(user);
		return use;
	}

	@Override
	public User getUserById(Integer id) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new BadRequestException("User not retrieved with id : " + id));
		return user;
	}

	@Override
	public void deleteUserById(Integer id) {
		User user = userRepository.findById(id).get();
		if (user != null) {
			userRepository.delete(user);
		}
	}

	@Override
	public User updateUserById(User user, Integer id) {
		User existingUser = userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with id : " + id));
		existingUser.setUserName(user.getUserName());
		existingUser.setUserCity(user.getUserCity());
		return userRepository.save(existingUser);
	}

	@Override
	public List<User> getListByCity(String city) {
		List<User> userList = userRepository.findByCity(city);
		return userList;
	}

}
