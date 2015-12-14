package com.springdemo.module.usermgr.dao;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.springdemo.core.BaseDao;
import com.springdemo.module.usermgr.vo.SUser;

@Repository("userMgrDao")
@SuppressWarnings("unchecked")
public class UserMgrDao extends BaseDao{

	public SUser addUser(final SUser sUser){
		  sUser.setId(super.saveAndReturnKey(sUser).intValue());
		  return sUser; 
	}

	public List<SUser> getUsers() {
		return getJdbcTemplate().query("select *  from suser", new BeanPropertyRowMapper(SUser.class));
	}
	public SUser getUserByColunm(String columnName, Object value) {
		return getJdbcTemplate().queryForObject("select *  from suser where "+columnName+"=?", new BeanPropertyRowMapper(SUser.class),value);
	}
}
