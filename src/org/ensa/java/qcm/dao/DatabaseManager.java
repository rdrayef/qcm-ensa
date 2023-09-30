package org.ensa.java.qcm.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {
	private static final String DB_URL = "jdbc:mysql://localhost:3306/qcm-ensa";
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = "";

	public static Connection getConnection() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
}
