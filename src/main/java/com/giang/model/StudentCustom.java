package com.giang.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class StudentCustom {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "student_id")
	private int studentId;
	@Column(name = "student_name")
	private String studentName;
	@Column(name = "student_code")
	private String studentCode;
	@Column(name = "address")
	private String address;
	@Column(name = "average_score")
	private double averageScore;
	@Column(name = "date_of_birth")
	private Date dayOfBirth;
	@Column(name = "sex")
	private String sex;
	@Column(name = "className")
	private String className;
	@Column(name = "gradeName")
	private String gradeName;
	

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getStudentCode() {
		return studentCode;
	}

	public void setStudentCode(String studentCode) {
		this.studentCode = studentCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public double getAverageScore() {
		return averageScore;
	}

	public void setAverageScore(double averageScore) {
		this.averageScore = averageScore;
	}

	public Date getDayOfBirth() {
		return dayOfBirth;
	}

	public void setDayOfBirth(Date dayOfBirth) {
		this.dayOfBirth = dayOfBirth;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getGradeName() {
		return gradeName;
	}

	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}

	public StudentCustom(int studentId, String studentName, String studentCode, String address, double averageScore,
			Date dayOfBirth, String sex, String className, String gradeName) {
		super();
		this.studentId = studentId;
		this.studentName = studentName;
		this.studentCode = studentCode;
		this.address = address;
		this.averageScore = averageScore;
		this.dayOfBirth = dayOfBirth;
		this.sex = sex;
		this.className = className;
		this.gradeName = gradeName;
	}

	public StudentCustom() {
		super();
	}

}
