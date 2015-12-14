package com.hxj.dao.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.hxj.dao.DAO;
import com.hxj.po.Student;
import com.hxj.util.HibernateSessionFactory;

public class StudentDAOImpl implements DAO {

	public boolean delete(Object o) {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tran = null;
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
		} finally {

			HibernateSessionFactory.closeSession();
		}

		return false;
	}

	public boolean save(Object o) {
		Session session = null;
		Transaction tran = null;
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
		} finally {

			HibernateSessionFactory.closeSession();
		}

		return false;
	}

	public boolean update(Object o) {
		Session session = null;
		Transaction tran = null;
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
		} finally {

			HibernateSessionFactory.closeSession();
		}

		return false;
	}

	public List findByAll() {

		Session session = null;
	//	Transaction tran = null;
		String hql = " from Student ";
		List list = new ArrayList();
		try {
			session = HibernateSessionFactory.getSession();
		//	tran = session.beginTransaction();
			Query q = session.createQuery(hql);
			list = q.list();
	//		tran.commit();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		//	tran.rollback();
		} finally {

		//	HibernateSessionFactory.closeSession();
		}

		return list;
	}
    
	public List find(){
		
		Session session = null;
	
			List list = new ArrayList();
			
			try {
				session = HibernateSessionFactory.getSession();
	
				Criteria cri = session.createCriteria(Student.class);
				cri.setFirstResult(3);
				cri.setMaxResults(4);
				list = cri.list();
	

			} catch (Exception e) {
			
				e.printStackTrace();
			
			}

			return list;
	}
	
	public Student findByName(String name){
		Session session = null;
		String hql = " from Student where name = ?" ;
		try {
			session = HibernateSessionFactory.getSession();
			Query q = session.createQuery(hql);
			q.setString(0,name);
			Iterator ite = q.iterate();
			if(ite.hasNext()){
				return (Student)ite.next();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
}
