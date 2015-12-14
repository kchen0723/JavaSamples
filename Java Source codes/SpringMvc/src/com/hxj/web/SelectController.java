package com.hxj.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.hxj.dao.impl.StudentDAOImpl;
import com.hxj.po.Student;

public class SelectController extends SimpleFormController {

	public SelectController(){
		this.setCommandClass(Student.class);
	}
	
	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {

		String name = request.getParameter("username");
		StudentDAOImpl student = new StudentDAOImpl();
		Student stu = student.findByName(name);
		return new ModelAndView("show.jsp", "student", stu);
	}

}
