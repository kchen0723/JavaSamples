package com.hxj.po;

/**
 * Student entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class Student implements java.io.Serializable {

	// Fields

	private Integer id;
	private Classes classes;
	private String name;

	// Constructors

	/** default constructor */
	public Student() {
	}

	/** full constructor */
	public Student(Classes classes, String name) {
		this.classes = classes;
		this.name = name;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Classes getClasses() {
		return this.classes;
	}

	public void setClasses(Classes classes) {
		this.classes = classes;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}