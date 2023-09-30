package org.ensa.java.qcm.ui.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.ensa.java.qcm.business.AuthService;
import org.ensa.java.qcm.models.User;
import org.ensa.java.qcm.ui.QCMAppUI;
import org.ensa.java.qcm.ui.auth.ConnexionUI;

public class LoginListener implements ActionListener {
	private ConnexionUI parent;
	private AuthService authService;

	public LoginListener(ConnexionUI parent) {
		this.authService = new AuthService();
		this.parent = parent;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// Handle the "Login" button click
		String nomUtilisateur = parent.getNomUtilisateur();
		String motDePasse = parent.getMotDePasse();

		User authenticateUser = authService.authenticateUser(nomUtilisateur, motDePasse);

		// Simulated authentication logic
		if (authenticateUser!=null) {
			parent.setVisible(false); // Hide the login screen
			QCMAppUI appUI = new QCMAppUI(authenticateUser);
			appUI.setVisible(true); // Show the main application screen
		} else {
			parent.displayLoginFailureMessage();
		}
	}
}