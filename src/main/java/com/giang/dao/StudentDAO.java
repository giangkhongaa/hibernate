package com.giang.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.giang.model.Student;
import com.giang.model.StudentCustom;
import com.giang.model.Class;

/**
 * Performs interaction with the database
 * 
 * @author Giang
 *
 */

public interface StudentDAO {

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
	List<Student> getStudentlist(int firstResult, int maxResult);

	/**
	 * Get all class data
	 * 
	 * @return List<Class>
	 *
	 */
	List<Class> getClassList();

	/**
	 * Add students to the database
	 * 
	 * @param Student
	 *
	 */
	void insertStudent(Student student);

	/**
	 * Edit students with corresponding studentId
	 * 
	 * @param Student
	 *
	 */
	void updateStudent(Student student);

	/**
	 * Remove student from data
	 * 
	 * @param int
	 *
	 */
	void deleteStudent(int studentId);

	/**
	 * Search student information with corresponding id , to use for update
	 * 
	 * @param int
	 *
	 * @return Student
	 */
	Student findStudentbyId(int studentId);

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
	List<Student> findStudentsByAll(Student student, Date dateStart, Date dateEnd, int firstResult, int maxResult);

	/**
	 * Search class information with corresponding id
	 * 
	 * @param int
	 *
	 * @return Class
	 */
	Class findClassById(int id);

	/**
	 * Count all students
	 *
	 * @return int
	 */
	int countAllStudent();

	/**
	 * Count all students with condition
	 * 
	 * @param Student
	 * @param Date
	 * @param Date
	 *
	 * @return int
	 */
	int countStudentsByAll(Student student, Date dateStart, Date dateEnd);

	String getMessage();

	void setMessage(String message);

	List<StudentCustom> showStudentCustom(int firstResult, int maxResult);

}
