package com.giang.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

public class Grade {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "grade_id")
	private int gradeId;
	@Column(name = "gradeName")
	private String gradeName;
	@OneToMany(mappedBy = "Class")
	@Fetch(FetchMode.JOIN)
	private Set<Class> classOfGrade;

	
	public int getGradeId() {
		return gradeId;
	}

	public void setGradeId(int gradeId) {
		this.gradeId = gradeId;
	}

	public String getGradeName() {
		return gradeName;
	}

	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}

	public Set<Class> getclassOfGrade() {
		return classOfGrade;
	}

	public void setclassOfGrade(Set<Class> classOfGrade) {
		this.classOfGrade = classOfGrade;
	}

	public Grade(int gradeId, String gradeName, Set<Class> classOfGrade) {
		super();
		this.gradeId = gradeId;
		this.gradeName = gradeName;
		this.classOfGrade = classOfGrade;
	}

	public Grade() {
		super();
	}
}
