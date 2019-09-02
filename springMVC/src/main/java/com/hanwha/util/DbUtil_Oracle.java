package com.hanwha.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DbUtil_Oracle {
	//�Լ�1: ����̹� �ε� + Ŀ�ؼ� ����
	public static Connection getConnect() {
		Connection conn = null;
		
		String url = "jdbc:oracle:thin:@localhost:1521:hworacle";
		String user = "hr";
		String password = "hr";
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	public static void dbClose(ResultSet rs, Statement st, Connection conn) {
		try {
			if(rs!=null) rs.close();
			if(st!=null) st.close();
			if(conn!=null) conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
