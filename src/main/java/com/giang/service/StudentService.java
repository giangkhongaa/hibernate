package com.giang.service;

import java.util.Date;
import java.util.List;

import com.giang.model.Class;
import com.giang.model.Student;
import com.giang.model.StudentCustom;

/**
 * Use word StudentDAO and used for Controller
 * 
 * @author Giang
 *
 */
public interface StudentService {

	/**
	 * Get all student data within limits Taken from the location of the record
	 * and the number of records
	 * 
	 * @param int
	 * @param int
	 * 
	 * @return List<Student>
	 *
	 */
	List<Student> getStudentlistSercive(int firstResult, int maxResult);

	/**
	 * Get all class data
	 * 
	 * @return List<Class>
	 *
	 */
	List<Class> getClassListService();

	/**
	 * Add students to the database
	 * 
	 * @param Student
	 *
	 */
	void insertStudentService(Student student);

	/**
	 * Edit students with corresponding studentId
	 * 
	 * @param Student
	 *
	 */
	void updateStudentService(Student student);

	/**
	 * Remove student from data
	 * 
	 * @param int
	 *
	 */
	void deleteStudentService(int studentId);

	/**
	 * Search student information with corresponding id , to use for update
	 * 
	 * @param int
	 *
	 * @return Student
	 */
	Student findStudentbyIdService(int studentId);

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
	List<Student> findStudentByAllService(Student student, Date dateStart, Date dateEnd, int firstResult,
			int maxResult);

	/**
	 * Search class information with corresponding id
	 * 
	 * @param int
	 *
	 * @return Class
	 */
	Class findClassByIdService(int id);

	/**
	 * Count all students
	 *
	 * @return int
	 */
	int countAllStudentService();

	/**
	 * Count all students with condition
	 * 
	 * @param Student
	 * @param Date
	 * @param Date
	 *
	 * @return int
	 */
	int countStudentsByAllService(Student student, Date dateStart, Date dateEnd);

	String getMessage();

	void setMessage(String message);

//	String convertEndcodingReverse(String string);
//
//	String convertEndcoding(String string);

	List<StudentCustom> showStudentCustomService(int firstResult, int maxResult);
}
