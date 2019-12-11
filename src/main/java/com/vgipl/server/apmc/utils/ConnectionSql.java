package com.vgipl.server.apmc.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConnectionSql {

private static Connection connection=null;
	
	public static Connection getConnection(String dbUser,String dbPassword ) {
		try {

			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");  
			connection = DriverManager.getConnection(DbConstant.DB_URL, dbUser, dbPassword);
			
		}catch(SQLException e) {
			e.printStackTrace();
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		return connection;
	
	}

	public static void closeConnection() {
		if(connection!=null)
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
}
