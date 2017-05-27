package com.giang.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name = "class")
public class Class {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "class_id")
	private int classId;
	@Column(name = "className")
	private String className;
	@OneToMany(mappedBy = "Student")
	@Fetch(FetchMode.JOIN)
	private Set<Student> students;

	@ManyToOne
	@Fetch(FetchMode.JOIN)
	private Grade grade;

	
//	private double avgScore;

	public int getClassId() {
		return classId;
	}

	public void setClassId(int classId) {
		this.classId = classId;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public Set<Student> getStudents() {
		return students;
	}

	public void setStudents(Set<Student> students) {
		this.students = students;
	}

	public Grade getGrade() {
		return grade;
	}

	public Class(int classId, String className, Set<Student> students, Grade grade) {
		super();
		this.classId = classId;
		this.className = className;
		this.students = students;
		this.grade = grade;
	}

	public void setGrade(Grade grade) {
		this.grade = grade;
	}

	public Class() {
		super();
	}

	public double getAvgScore() {
//		avgScore = 0;
		if (students == null) {
			return 0;
		} else {
			double sum = 0;
			for (Student student : students) {
				sum += student.getAverageScore();
			}
			return sum / students.size();
		}

	}


}
