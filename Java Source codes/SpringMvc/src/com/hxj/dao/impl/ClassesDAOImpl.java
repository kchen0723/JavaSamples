package com.hxj.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.hxj.dao.DAO;
import com.hxj.util.HibernateSessionFactory;

public class ClassesDAOImpl implements DAO {

	public boolean delete(Object o) {
		// TODO Auto-generated method stub
	    Session session = null;
	    Transaction tran  = null;
	    try {
	    	session = HibernateSessionFactory.getSession(); 
	    	tran = session.beginTransaction();
	    	session.delete(o);
	    	tran.commit();
	    	return true;
	    	
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			tran.rollback();
		}finally{
			
			HibernateSessionFactory.closeSession();
		}
	   
		return false;
	}

	public boolean save(Object o) {
		 Session session = null;
		    Transaction tran  = null;
		    try {
		    	session = HibernateSessionFactory.getSession(); 
		    	tran = session.beginTransaction();
		    	session.save(o);
		    	tran.commit();
		    	return true;
		    	
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				tran.rollback();
			}finally{
				
				HibernateSessionFactory.closeSession();
			}
		   
			return false;
	}

	public boolean update(Object o) {
		 Session session = null;
		    Transaction tran  = null;
		    try {
		    	session = HibernateSessionFactory.getSession(); 
		    	tran = session.beginTransaction();
		    	session.update(o);
		    	tran.commit();
		    	return true;
		    	
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				tran.rollback();
			}finally{
				
				HibernateSessionFactory.closeSession();
			}
		   
			return false;
	}

	public List findByAll() {
			Session session = null;
		   // Transaction tran  = null;
		    String hql = " from Classes ";
		    List list = new ArrayList();
		    try {
		    	session = HibernateSessionFactory.getSession(); 
		    //	tran = session.beginTransaction();
		    	Query q =  session.createQuery(hql);
		    	list = q.list();
		   // 	tran.commit();
		    	
		    	
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			//	tran.rollback();
			}finally{
				
			//	HibernateSessionFactory.closeSession();
			}
		
		return list;
	}

	
}
