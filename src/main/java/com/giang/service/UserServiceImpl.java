package com.giang.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.giang.dao.UserDAO;
import com.giang.model.User;
import com.giang.security.MyUserDetails;

public class UserServiceImpl implements UserDetailsService, UserService {

	private UserDAO userDao;

	public UserServiceImpl(UserDAO userDao) {
		this.userDao = userDao;
	}

	/**
	 * Obtain the rights of the user
	 * 
	 * @param String
	 * 
	 * @return List<GrantedAuthority>
	 *
	 */
	public List<GrantedAuthority> getGrantedAuthorities(String role) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority(role));
		return authorities;
	}

	/**
	 * It serves to log on to the system, providing an a, for decentralization
	 * and status
	 * 
	 * @param String
	 * 
	 * @return UserDetails
	 *
	 */
	@Override
	public UserDetails loadUserByUsername(String user) throws UsernameNotFoundException {
		com.giang.model.User userResult = userDao.findByUserName(user);
		return new MyUserDetails(userResult.getUsername(), userResult.getPassword(),
				getGrantedAuthorities(userResult.getRole()));
	}

	/**
	 * find all User by userName (searck kind like)
	 * 
	 * @return List<User>
	 *
	 */
	@Override
	public List<User> findUserListByUserNameService(String username) {
		return userDao.findUserListByUsername(username);
	}

	@Override
	public void updateUserService(User user) {
		userDao.updateUser(user);

	}

	@Override
	public User findUserByIdService(int userId) {
		return userDao.findUserById(userId);
	}

}
