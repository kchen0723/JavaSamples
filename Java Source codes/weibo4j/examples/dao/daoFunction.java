package dao;

import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.swing.table.DefaultTableModel;

import statisticsModel.SimpleStatistics;

public class daoFunction {
	//从数据库读取一行
	public void RowSingleWrite(DefaultTableModel model,String sql,int[] norow,int columns,int gap){
		ResultSet rt=null;
		rt = dao.SqlExcute(sql);  
		SimpleStatistics ss=new SimpleStatistics();
		
		ResultSetMetaData rsmd=null;
		try {
			rsmd = rt.getMetaData();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		Object[] temp=new Object[columns];
		
		try {
			while(rt.next()){ 
				for(int i=gap;i<columns+gap;i++){
					try {
						if(ss.NoRowCheck(i, norow)){
							int str=rt.getInt(rsmd.getColumnName(i));
							temp[i-gap]=str;
							
						}else{
							String str=new String(rt.getBytes(rsmd.getColumnName(i)),"gbk");
							temp[i-gap]=str;
							
						}
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
	 			model.addRow(temp);
	  			}
	  	} catch (SQLException e) {
		// TODO Auto-generated catch block
			e.printStackTrace();
	  	}try {
	  		rt.close();
	  	} catch (SQLException e) {
			// TODO Auto-generated catch block
	  		e.printStackTrace();
	  	}
	  	rt=null;
	}
}