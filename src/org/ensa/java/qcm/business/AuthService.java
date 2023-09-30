package org.ensa.java.qcm.business;

import org.ensa.java.qcm.dao.DatabaseManager;
import org.ensa.java.qcm.dao.UserDAO;
import org.ensa.java.qcm.models.User;

public class AuthService {
	private UserDAO userDAO;

	public AuthService() {
		this.userDAO = new UserDAO(DatabaseManager.getConnection());
	}

	public User authenticateUser(String email, String password) {
		User user = userDAO.getUserByEmail(email);
		System.out.println("iiii"+user.getId());
		if (user != null && user.getPassword().equals(password)) {
			return user; // Authentication successful
		}
		return null; // Authentication failed
	}
}
