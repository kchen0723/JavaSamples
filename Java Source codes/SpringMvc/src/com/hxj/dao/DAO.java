package com.hxj.dao;

import java.util.List;

public interface DAO {
	
	public boolean save(Object o);
	
	public boolean delete(Object o);

	public boolean update(Object o);
	
	public List findByAll();

}
