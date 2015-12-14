package com.hxj.po;

import java.util.HashSet;
import java.util.Set;

/**
 * Classes entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class Classes implements java.io.Serializable {

	// Fields

	private Integer id;
	private String name;
	private Set students = new HashSet(0);

	// Constructors

	/** default constructor */
	public Classes() {
	}

	/** full constructor */
	public Classes(String name, Set students) {
		this.name = name;
		this.students = students;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set getStudents() {
		return this.students;
	}

	public void setStudents(Set students) {
		this.students = students;
	}

}