package com.hxj.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.hxj.dao.impl.StudentDAOImpl;

public class ShowController implements Controller{

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		
		StudentDAOImpl student = new StudentDAOImpl();
		List studentList  =  student.findByAll();
		
		return new ModelAndView("select.jsp","student",studentList);
	}

	

}
