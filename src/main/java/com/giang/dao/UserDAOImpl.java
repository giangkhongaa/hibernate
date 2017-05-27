package com.giang.dao;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.giang.model.User;

public class UserDAOImpl implements UserDAO {
	private SessionFactory sessionFactory;
	final static Logger logger = Logger.getLogger(UserDAOImpl.class);

	public UserDAOImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public UserDAOImpl() {
	}

	/**
	 * Search User information with corresponding userName
	 * 
	 * @param String
	 *
	 * @return User
	 */
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public User findByUserName(String username) {
		logger.info("function :findByUserName - Performing the function get User have username :" + username);
		try {
			// Initialize Criterria with object User
			Criteria criteria = sessionFactory.getCurrentSession().createCriteria(com.giang.model.User.class);
			// add condition for Criteria
			criteria.add(Restrictions.eq("username", username));
			// Pressed type Criteria to List<User>
			List<User> userList = (List<User>) criteria.list();
			// check list<User> has value or no
			if (userList.size() > 0) {
				return userList.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * find all User by userName (searck kind like)
	 * 
	 * @return List<User>
	 *
	 */
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public List<User> findUserListByUsername(String username) {
		try {

			logger.info("function :finduserListbyUsername - Performing the function displays all User have username :"
					+ username);
			// get all User has userName like param
			List<User> userList = (List<User>) sessionFactory.getCurrentSession().createCriteria(User.class)
					.add(Restrictions.like("username", "%" + username + "%")).addOrder(Order.asc("id")).list();
			return userList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<User>();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void updateUser(User user) {
		Session session = null; // Session initialization equal null
		Transaction transaction = null;// Transaction initialization equal null
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();// begin transaction

			// find user with corresponding id user of user param
			User userUpdate = (User) session.get(User.class, user.getId());

			// set value properties
			userUpdate.setUsername(user.getUsername());
			if (user.getPassword().length() == 32) {
				userUpdate.setPassword(user.getPassword());
			} else {
				userUpdate.setPassword(convertMD5(user.getPassword()));
			}
			userUpdate.setRole(user.getRole());
			// update user
			session.update(userUpdate);
			transaction.commit(); // Update data to database
			logger.info("update success");
		} catch (Exception e) {
			transaction.rollback(); // rollback data

			logger.info("update failed");
			logger.error("error: ", e);
		} finally {
			if (session != null) {
				session.close(); // close session
			}
		}
	}

	@Override
	public User findUserById(int userId) {
		Session session = null; // Session initialization equal null
		Transaction transaction = null;// Transaction initialization equal null
		try {
			session = sessionFactory.openSession();

			// find user with corresponding id user of user param
			User user = (User) session.get(User.class, userId);

			logger.info("findUserById success");
			return user;
		} catch (Exception e) {

			logger.info("findUserById failed");
			logger.error("error: ", e);
		} finally {
			if (session != null) {
				session.close(); // close session
			}
		}
		return null;
	}

	private String convertMD5(String string) {
		MessageDigest messageDigest = null;
		try {
			messageDigest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		messageDigest.update(string.getBytes(), 0, string.length());
		String hashedPass = new BigInteger(1, messageDigest.digest()).toString(16);
		if (hashedPass.length() < 32) {
			hashedPass = "0" + hashedPass;
		}
		return hashedPass;
	}

}
