package org.ensa.java.qcm;

import javax.swing.SwingUtilities;

import org.ensa.java.qcm.ui.auth.ConnexionUI;

public class Main {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			ConnexionUI connexionUI = new ConnexionUI();
			connexionUI.setVisible(true);
		});
	}

}
