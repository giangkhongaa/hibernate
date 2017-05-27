package com.giang.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "student")
public class Student {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "student_id")
	private int studentId;
	@Column(name = "student_name")
	@NotEmpty
	private String studentName;
	@Column(name = "student_code")
	@Size(min = 2, max = 10)
	private String studentCode;
	@Column(name = "address")
	@Size(min = 2, max = 200)
	private String address;
	@Column(name = "average_score")
	@NotNull
	@DecimalMax("10.0")
	@DecimalMin("0.0")
	private double averageScore;
	@Column(name = "date_of_birth")
	@NotNull(message = "Please enter a date")
	@Past(message = "Only the past is valid")
	private Date dayOfBirth;
	@NotEmpty
	private String sex;

	@ManyToOne
	@Fetch(FetchMode.JOIN)
	private Class classStudent;

	public Class getClassStudent() {
		return classStudent;
	}

	public void setClassStudent(Class classStudent) {
		this.classStudent = classStudent;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	

	public Student(int studentId, String studentName, String studentCode, String address, double averageScore,
			Date dayOfBirth, Class classStudent) {
		super();
		this.studentId = studentId;
		this.studentName = studentName;
		this.studentCode = studentCode;
		this.address = address;
		this.averageScore = averageScore;
		this.dayOfBirth = dayOfBirth;
		this.classStudent = classStudent;
	}

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

	public Student() {
		super();
		this.sex = "No";
		this.studentName="";
	}

}
