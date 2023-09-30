package org.ensa.java.qcm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.ensa.java.qcm.models.User;

public class UserDAO {
	private Connection connection;

	public UserDAO(Connection connection) {
		this.connection = connection;
	}

	public User getUserByEmail(String email) {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			String query = "SELECT * FROM users WHERE email = ?";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, email);

			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				String retrievedEmail = resultSet.getString("email");
				String password = resultSet.getString("password");
				String role = resultSet.getString("role");
				int id = resultSet.getInt("id");
				System.out.println("isss" + id);
				return new User(id, retrievedEmail, password, role);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null; // User not found or an error occurred
	}
}
