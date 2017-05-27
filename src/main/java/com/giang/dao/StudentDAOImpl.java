package com.giang.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.giang.model.Class;
import com.giang.model.Student;
import com.giang.model.StudentCustom;

/**
 * This is a deployment class of StudentDAO, and performs direct manipulation
 * with the database.
 * 
 * @author Giang
 *
 */
public class StudentDAOImpl implements StudentDAO {
	private SessionFactory sessionFactory;// Variable manipulation with data

	// Initialize a logger counter ,actually generated the information under
	// console
	final static Logger logger = Logger.getLogger(StudentDAOImpl.class);

	private String message;// show notification excute insert, update , delete

	public StudentDAOImpl(SessionFactory sessionFactory) {
		this.setSessionFactory(sessionFactory);
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/**
	 * Get students data within limits Taken from the location of the record and
	 * the number of records
	 * 
	 * @param int
	 * @param int
	 * 
	 * @return List<Student>
	 *
	 */
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Student> getStudentlist(int firstResult, int maxResult) {
		logger.info("function :getStudentlist - Performing the function displays all students ");
		Session session = null; // Session initialization equal null
		try {
			session = sessionFactory.openSession();
			// with Criteria
			// get students from database with limits
			// firstResult: position begin
			// maxResult : number record get
			// List<Student> studentList = (List<Student>)
			// sessionFactory.getCurrentSession().createCriteria(Student.class)
			// .setFetchMode("classStudent",
			// FetchMode.JOIN).addOrder(Order.asc("studentId"))
			// .setFirstResult(firstResult).setMaxResults(maxResult).list();

			// HQL
			String hql = "select student from Student student " + "join fetch student.classStudent classStudent "
					+ "join fetch classStudent.grade order by student.studentId ";
			Query query = session.createQuery(hql);
			query.setFirstResult(firstResult);
			query.setMaxResults(maxResult);
			List<Student> studentList = query.list();

			// // SQL in hibernate
			// List<Student> studentList = new ArrayList<Student>();
			// String hql = "SELECT {s.*},{c.*},{g.*} FROM Student s "
			// + "JOIN Class c on s.class_id = c.class_id "
			// + "JOIN Grade g on g.grade_id = c.grade_id "
			// + "order by s.student_id";
			// SQLQuery query = session.createSQLQuery(hql).addEntity("s",
			// Student.class).addJoin("c", "s.classStudent").addJoin("g",
			// "c.grade");
			// query.setFirstResult(firstResult);
			// query.setMaxResults(maxResult);
			// List<Object[]> rows = query.list();
			// for (Object[] row : rows) {
			// studentList.add((Student) row[0]);
			// }
			return studentList;
		} catch (Exception e) {
			// display logger
			logger.info("insert failed");
			logger.error("error: ", e);
		} finally {
			if (session != null) {
				session.close(); // close session
			}
		}
		return new ArrayList<Student>();
	}

	/**
	 * Get all class data
	 * 
	 * @return List<Class>
	 *
	 */
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Class> getClassList() {
		Session session = null; // Session initialization equal null
		try {
			session = sessionFactory.openSession();
			// get all class at database
			List<Class> classList = (List<Class>) session.createCriteria(Class.class).list();

			// display logger
			logger.info("getClassList: success");

			return classList;
		} catch (Exception e) {

			// display logger
			logger.info("insert failed");
			logger.error("error: ", e);
		} finally {
			if (session != null) {
				session.close(); // close session
			}
		}
		return new ArrayList<Class>();
	}

	/**
	 * Add students to the database
	 * 
	 * @param Student
	 *
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void insertStudent(Student student) {
		Session session = null; // Session initialization equal null
		Transaction transaction = null; // Transaction initialization equal null
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction(); // begin transaction
			// get class of student
			Class classStudent = (Class) session.get(Class.class, student.getClassStudent().getClassId());
			// set Class for Student
			student.setClassStudent(classStudent);
			// insert in database
			session.save(student);
			transaction.commit(); // Update data to database
			logger.info("insert success");
			message = "Insert student success !!!";
		} catch (Exception e) {
			transaction.rollback(); // rollback data
			message = "Insert student error !!!";
			logger.info("insert failed");
			logger.error("error: ", e);
		} finally {
			if (session != null) {
				session.close();// close session
			}
		}
	}

	/**
	 * Edit students with corresponding studentId
	 * 
	 * @param Student
	 *
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void updateStudent(Student student) {
		Session session = null; // Session initialization equal null
		Transaction transaction = null;// Transaction initialization equal null
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();// begin transaction

			// find Student with corresponding id student of student param
			Student studentUpdate = (Student) session.get(Student.class, student.getStudentId());
			// find Class with corresponding id class of student param
			Class classStudent = (Class) session.get(Class.class, student.getClassStudent().getClassId());

			// set value properties
			studentUpdate.setStudentName(student.getStudentName());
			studentUpdate.setStudentCode(student.getStudentCode());
			studentUpdate.setAddress(student.getAddress());
			studentUpdate.setAverageScore(student.getAverageScore());
			studentUpdate.setDayOfBirth(student.getDayOfBirth());
			studentUpdate.setSex(student.getSex());
			studentUpdate.setClassStudent(classStudent);

			// update student
			session.update(studentUpdate);
			transaction.commit(); // Update data to database
			message = "Update student success !!!";
			logger.info("update success");
		} catch (Exception e) {
			transaction.rollback(); // rollback data
			message = "Update student error !!!";
			logger.info("update failed");
			logger.error("error: ", e);
		} finally {
			if (session != null) {
				session.close(); // close session
			}
		}

	}

	/**
	 * Remove student from data
	 * 
	 * @param int
	 *
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteStudent(int studentId) {
		Session session = null; // Session initialization equal null
		Transaction transaction = null; // Transaction initialization equal null
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction(); // begin transaction

			// find Student with corresponding id student param
			Student student = (Student) session.get(Student.class, studentId);

			// delete student
			session.delete(student);

			// text Transaction
			// int a= Integer.parseInt("aaa");

			// update data to database
			transaction.commit();

			// display logger
			logger.info("delete success");

			// update message
			message = "delete student success !!!";

		} catch (Exception e) {
			transaction.rollback(); // rollback data
			message = "delete student error !!!";
			// display loggers
			logger.info("delete failed");
			logger.error("error: ", e);
		} finally {
			if (session != null) {
				session.close(); // closse session
			}
		}

	}

	/**
	 * Search student information with corresponding id , to use for update,show
	 * in update, delete
	 * 
	 * @param int
	 *
	 * @return Student
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Student findStudentbyId(int studentId) {
		logger.info("findStudentbyId: Search student information with corresponding id ");
		Session session = null; // Session initialization equal null
		try {
			session = sessionFactory.openSession();

			// find Student with corresponding id student param
			Student student = (Student) session.get(Student.class, studentId);

			return student;
		} catch (Exception e) {

			// displays logger
			logger.error("error: ", e);
		} finally {
			if (session != null) {
				session.close(); // close session
			}
		}
		return new Student();
	}

	/**
	 * Search all students with corresponding cases within limits. #case
	 * 1:Search by name #case 2:Search by name and Score (larger than) #case
	 * 3:Search by name and between two date #case 4:Search by name and Sex
	 * #case 5:Search by name and Class .................................. #case
	 * n: search all 5 attributes
	 * 
	 * @param Student
	 * @param Date
	 * @param Date
	 * @param int
	 * @param int
	 *
	 * @return List<Student>
	 */
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public List<Student> findStudentsByAll(Student student, Date startDate, Date endDate, int firstResult,
			int maxResult) {

		logger.info("findStudentsByAll: Search students with corresponding cases within limits ");
		Session session = null; // Session initialization equal null
		try {
			session = sessionFactory.openSession();
			////////////////////////////////////////////////////////////////////////////////////////////////////////////
			// find use Criteria
			// initialize criteria with object Student
			// Criteria criteria = session.createCriteria(Student.class);
			// // setFetchMode : join betwwen two table
			// // because search averageScore kind like larger than, so Always
			// // addOrder : kind like Order.asc sort ascending follow studentId
			// criteria.setFetchMode("classStudent",
			// FetchMode.JOIN).setFirstResult(firstResult).setMaxResults(maxResult)
			// .addOrder(Order.asc("studentId")).add(Restrictions.ge("averageScore",
			// student.getAverageScore()));
			// // Case search students by name
			// if (!student.getStudentName().equals("")) {
			// criteria.add(Restrictions.like("studentName", "%" +
			// student.getStudentName() + "%"));
			// }
			// // Case search students by Sex
			// if (!student.getSex().equals("No")) {
			// criteria.add(Restrictions.eq("sex", student.getSex()));
			// }
			// // Case search students by Class
			// if (student.getClassStudent() != null) {
			// criteria.add(Restrictions.eq("classStudent",
			// student.getClassStudent()));
			// }
			// // Case search students by dayofbirth equal between startDate and
			// // endDate
			// if (startDate != null && endDate != null) {
			// criteria.add(Restrictions.between("dayOfBirth", startDate,
			// endDate));
			// }
			// return (List<Student>) criteria.list();
			// end use Criteria
			///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

			////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			// find use HQL

			// String hql = "select student from Student student "
			// + "join fetch student.classStudent classStudent "
			// + "join fetch classStudent.grade "
			// + "where student.averageScore >= :averageScore";
			//
			// if (!student.getStudentName().equals("")) {
			// hql += " and student.studentName like :studentName";
			// }
			// // Case search students by Sex
			// if (!student.getSex().equals("No")) {
			// hql += " and student.sex = :sex";
			// }
			// // Case search students by Class
			// if (student.getClassStudent() != null) {
			// hql += " and student.classStudent = :classStudent ";
			// }
			// // Case search students by dayofbirth equal between startDate and
			// // endDate
			// if (startDate != null && endDate != null) {
			// hql += " and student.dayOfBirth BETWEEN :startDate AND :endDate";
			// }
			//
			//
			// Query query = session.createQuery(hql);
			// query.setParameter("averageScore", student.getAverageScore());
			//
			// if (!student.getStudentName().equals("")) {
			// query.setParameter("studentName", "%" + student.getStudentName()
			// + "%");
			// }
			// // Case search students by Sex
			// if (!student.getSex().equals("No")) {
			// query.setParameter("sex", student.getSex());
			// }
			// // Case search students by Class
			// if (student.getClassStudent() != null) {
			// query.setParameter("classStudent", student.getClassStudent());
			// }
			// // Case search students by dayofbirth equal between startDate and
			// // endDate
			// if (startDate != null && endDate != null) {
			// query.setParameter("startDate", startDate);
			// query.setParameter("endDate", endDate);
			// }
			// query.setFirstResult(firstResult);
			// query.setMaxResults(maxResult);
			// List<Student> studentList = query.list();
			// end find HQL
			/////////////////////////////////////////////////////////////////////////////////
			// Find use SQL
			String sql = "SELECT {s.*},{c.*},{g.*} FROM Student s " + "JOIN Class c on s.class_id = c.class_id "
					+ "JOIN Grade g on g.grade_id = c.grade_id " + "where s.average_score >= :averageScore";

			if (!student.getStudentName().equals("")) {
				sql += " and s.student_name like :studentName";
			}
			// Case search students by Sex
			if (!student.getSex().equals("No")) {
				sql += " and s.sex = :sex";
			}
			// Case search students by Class
			if (student.getClassStudent() != null) {
				sql += " and c.class_id = :classId ";
			}
			// Case search students by dayofbirth equal between startDate and
			// endDate
			if (startDate != null && endDate != null) {
				sql += " and s.date_of_birth BETWEEN :startDate AND :endDate";
			}

			SQLQuery query = session.createSQLQuery(sql);
			query.addEntity("s", Student.class).addJoin("c", "s.classStudent").addJoin("g", "c.grade");
			query.setParameter("averageScore", student.getAverageScore());

			if (!student.getStudentName().equals("")) {
				query.setParameter("studentName", "%" + student.getStudentName() + "%");
			}
			// Case search students by Sex
			if (!student.getSex().equals("No")) {
				query.setParameter("sex", student.getSex());
			}
			// Case search students by Class
			if (student.getClassStudent() != null) {
				query.setParameter("classId", student.getClassStudent().getClassId());
			}
			// Case search students by dayofbirth equal between startDate and
			// endDate
			if (startDate != null && endDate != null) {
				query.setParameter("startDate", startDate);
				query.setParameter("endDate", endDate);
			}

			query.setFirstResult(firstResult);
			query.setMaxResults(maxResult);
			List<Student> studentList = new ArrayList<Student>();
			List<Object[]> rows = query.list();
			for (Object[] row : rows) {
				studentList.add((Student) row[0]);
			}
			/////////////////////////////////////////////////////////////////////////////////

			return studentList;
		} catch (Exception e) {
			// displays logger
			logger.error("error: ", e);
		} finally {
			if (session != null) {
				session.close(); // close session
			}
		}
		return new ArrayList<Student>();
	}

	/**
	 * Search class information with corresponding id
	 * 
	 * @param int
	 *
	 * @return Class
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Class findClassById(int classId) {

		Session session = null;// Session initialization equal null
		try {
			session = sessionFactory.openSession();
			// find Class with corresponding id class param
			Class classStudent = (Class) session.get(Class.class, classId);

			return classStudent;
		} catch (Exception e) {

			// displays logger
			logger.error("error: ", e);
		} finally {
			if (session != null) {
				session.close();// close session
			}
		}
		return null;
	}

	/**
	 * Count all students
	 *
	 * @return int
	 */
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public int countAllStudent() {
		logger.info("function :countAllStudent - Count all students ");
		Session session = null; // Session initialization equal null
		try {
			session = sessionFactory.openSession();

			// get students from database with limits
			// firstResult: position begin
			// maxResult : number record get
			List<Student> studentList = (List<Student>) sessionFactory.getCurrentSession().createCriteria(Student.class)
					.setFetchMode("classStudent", FetchMode.JOIN).list();
			return studentList.size();
		} catch (Exception e) {

			// display logger
			logger.info("insert failed");
			logger.error("error: ", e);
		} finally {
			if (session != null) {
				session.close(); // close session
			}
		}
		return 0;
	}

	/**
	 * Count all students with condition
	 *
	 * @return int
	 */
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public int countStudentsByAll(Student student, Date startDate, Date endDate) {
		logger.info("findStudentsByAll: Search students with corresponding cases within limits ");
		Session session = null; // Session initialization equal null
		try {
			session = sessionFactory.openSession();

			// //find use Criteria
			// // initialize criteria with object Student
			Criteria criteria = session.createCriteria(Student.class);
			// setFetchMode : join betwwen two table
			// because search averageScore kind like larger than, so Always
			// addOrder : kind like Order.asc sort ascending follow studentId
			criteria.setFetchMode("classStudent", FetchMode.JOIN)
					.add(Restrictions.ge("averageScore", student.getAverageScore()));
			// Case search students by name
			if (!student.getStudentName().equals("")) {
				criteria.add(Restrictions.like("studentName", "%" + student.getStudentName() + "%"));
			}
			// Case search students by Sex
			if (!student.getSex().equals("No")) {
				criteria.add(Restrictions.eq("sex", student.getSex()));
			}
			// Case search students by Class
			if (student.getClassStudent() != null) {
				criteria.add(Restrictions.eq("classStudent", student.getClassStudent()));
			}
			// Case search students by dayofbirth equal between startDate and
			// endDate
			if (startDate != null && endDate != null) {
				criteria.add(Restrictions.between("dayOfBirth", startDate, endDate));
			}
			return ((List<Student>) criteria.list()).size();
			// end find Criteria

		} catch (Exception e) {

			// displays logger
			logger.error("error: ", e);
		} finally {
			if (session != null) {
				session.close(); // close session
			}
		}
		return 0;
	}

	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public void setMessage(String message) {
		this.message = message;

	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public List<StudentCustom> showStudentCustom(int firstResult, int maxResult) {
		try {
			// SQL in hibernate
			String hql = "select s.student_id, s.student_name, s.student_code, s.address, "
					+ "s.average_score, s.date_of_birth, s.sex, c.className, g.gradeName, AVG(s.average_score) AS avgScore"
					+ " from  class c, student s, grade g"
					+ " where s.class_id = c.class_id and c.grade_id = g.grade_id" + " order by s.student_id";
			SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(hql).addEntity(StudentCustom.class);
			query.setFirstResult(firstResult);
			query.setMaxResults(maxResult);
			List<StudentCustom> studentCustomList = query.list();
			return studentCustomList;
		} catch (Exception e) {

			// displays logger
			logger.error("error: ", e);
		}
		return new ArrayList<StudentCustom>();
	}

}
