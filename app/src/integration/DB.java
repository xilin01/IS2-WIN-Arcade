package integration;

import java.sql.*;

// Singleton Pattern
public class DB {
	private static final String DB = "e4win";
	private static final String LOGIN = "root";
	private static final String PASSWORD = "equipo4";
	private static final String URL = "jdbc:mysql://localhost/" + DB;
	
	private static Connection connection = null;

	private DB() {
	}

	public static Connection getConnection() {
		try {
			if(connection == null || connection.isClosed()) connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);
		} 
		catch(SQLException e) {
			e.printStackTrace();
			System.out.println("SQLException: " + e.getMessage());
			System.out.println("SQLState: " + e.getSQLState());
			System.out.println("VendorError: " + e.getErrorCode());
		}
		return connection;
	}
}
