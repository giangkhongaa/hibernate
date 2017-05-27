package com.giang.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.giang.dao.StudentDAO;
import com.giang.model.Class;
import com.giang.model.Student;
import com.giang.model.StudentCustom;

@Service("studentService")
public class StudentServiceImpl implements StudentService {

	@Autowired
	StudentDAO studentDAO;

	@Override
	public List<Student> getStudentlistSercive(int firstResult, int maxResult) {

		return studentDAO.getStudentlist(firstResult, maxResult);
	}

	@Override
	public List<Class> getClassListService() {

		return studentDAO.getClassList();
	}

	@Override
	public void insertStudentService(Student student) {
		studentDAO.insertStudent(student);
	}

	@Override
	public void updateStudentService(Student student) {
		studentDAO.updateStudent(student);

	}

	@Override
	public void deleteStudentService(int studentId) {
		studentDAO.deleteStudent(studentId);
	}

	@Override
	public Student findStudentbyIdService(int studentId) {

		return studentDAO.findStudentbyId(studentId);
	}

	@Override
	public List<Student> findStudentByAllService(Student student, Date startDate, Date endDate, int firstResult,
			int maxResult) {

		return studentDAO.findStudentsByAll(student, startDate, endDate, firstResult, maxResult);
	}

	@Override
	public Class findClassByIdService(int id) {

		return studentDAO.findClassById(id);
	}

	@Override
	public int countAllStudentService() {

		return studentDAO.countAllStudent();
	}

	@Override
	public int countStudentsByAllService(Student student, Date startDate, Date endDate) {

		return studentDAO.countStudentsByAll(student, startDate, endDate);
	}

	@Override
	public String getMessage() {
		return studentDAO.getMessage();
	}

	@Override
	public void setMessage(String message) {
		studentDAO.setMessage(message);

	}

//	@Override
//	public String convertEndcodingReverse(String string) {
//		String stringResult = "";
//		stringResult = string.replaceAll("&lt;", "<")
//				.replaceAll("&gt;", ">")
//				.replaceAll("&#39;", "'")
//				.replaceAll("&#x2F;", "/")
//				.replaceAll("&#x60;", "`")
//				.replaceAll("&#x3D;", "=");
//		return stringResult;
//
//	}
//
//	@Override
//	public String convertEndcoding(String string) {
//		String stringResult = "";
//		stringResult = string.replaceAll("<", "&lt;")
//				.replaceAll(">", "&gt;")
//				.replaceAll("'", "&#39;")
//				.replaceAll("/", "&#x2F;")
//				.replaceAll("`", "&#x60;")
//				.replaceAll("=", "&#x3D;");
//		return stringResult;
//	}

	@Override
	public List<StudentCustom> showStudentCustomService(int firstResult, int maxResult) {
		
		return studentDAO.showStudentCustom(firstResult, maxResult);
	}

}
