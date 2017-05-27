package com.giang.service;

import java.util.List;

import com.giang.model.User;

public interface UserService {
	/**
	 * find all User by userName (searck kind like)
	 * 
	 * @return List<User>
	 *
	 */
	List<User> findUserListByUserNameService(String username);

	User findUserByIdService(int userId);

	void updateUserService(User user);
}
