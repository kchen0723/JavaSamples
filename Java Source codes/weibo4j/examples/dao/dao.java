package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class dao {
	protected static String dbClassName = "sun.jdbc.odbc.JdbcOdbcDriver";
	public static Connection conn = null;
	public static String zipname=System.getProperty("user.dir")+"\\db\\db_weispace.MDB";
	protected static String dbUrl = "jdbc:odbc:driver={Microsoft Access Driver (*.mdb)};DBQ=";
	
	
	static {
		try {
			if (conn == null) {
				Class.forName(dbClassName);  
		        conn = DriverManager.getConnection(dbUrl+zipname);  
		        
			}
		} catch (Exception ee) {
			ee.printStackTrace();
		}
	}
	
	private dao() {
	}
	
	public static ResultSet query(String QueryStr) {
		ResultSet set = SqlExcute(QueryStr);
		return set;
	}
	
	public static ResultSet SqlExcute(String sql) {
		if (conn == null)
			return null;
		ResultSet rs = null;
		try {
			Statement stmt = null;
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	public static int update(String sql) {
		int result = 0;
		try {
			Statement stmt = conn.createStatement();
			result = stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static boolean insert(String sql) {
		boolean result = false;
		try {
			Statement stmt = conn.createStatement();
			result = stmt.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static List ResultList(String sql) {
		List<List> list = new ArrayList<List>();
		ResultSet rs = SqlExcute(sql);
		try {
			ResultSetMetaData metaData = rs.getMetaData();
			int colCount = metaData.getColumnCount();
			while (rs.next()) {
				List<String> row = new ArrayList<String>();
				for (int i = 1; i <= colCount; i++) {
					String str = rs.getString(i);
					if (str != null && !str.isEmpty())
						str = str.trim();
					row.add(str);
				}
				list.add(row);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
}
