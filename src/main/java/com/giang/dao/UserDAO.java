package com.giang.dao;

import java.util.List;

import com.giang.model.User;

public interface UserDAO {
	/**
	 * Search User information with corresponding userName
	 * 
	 * @param String
	 *
	 * @return User
	 */
	User findByUserName(String userName);

	/**
	 * find all User by userName (searck kind like)
	 * 
	 * @return List<User>
	 *
	 */
	List<User> findUserListByUsername(String userName);
	
	User findUserById(int userId);

	void updateUser(User user);
}
