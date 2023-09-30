package org.ensa.java.qcm.ui.auth;

import javax.swing.*;

import org.ensa.java.qcm.ui.listeners.LoginListener;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;

public class ConnexionUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTextField nomUtilisateurField;
	private JPasswordField motDePasseField;
	private JButton boutonConnexion;
	private LoginListener loginListener;

	public ConnexionUI() {
		setTitle("Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(400, 200);
		setLocationRelativeTo(null);

		// Create and set layout manager for the content pane
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout(10, 10)); // Add spacing between components

		// Create a panel to organize the input fields
		JPanel inputPanel = new JPanel(new GridLayout(2, 2, 5, 5)); // 2x2 grid layout with spacing

		// Create and add labels and input fields to the panel
		JLabel nomUtilisateurLabel = new JLabel("Username:");
		nomUtilisateurField = new JTextField();
		JLabel motDePasseLabel = new JLabel("Password:");
		motDePasseField = new JPasswordField();

		inputPanel.add(nomUtilisateurLabel);
		inputPanel.add(nomUtilisateurField);
		inputPanel.add(motDePasseLabel);
		inputPanel.add(motDePasseField);

		// Create and style the login button
		boutonConnexion = new JButton("Login");
		boutonConnexion.setBackground(Color.GREEN); // Change the button's background color

		// Add the login button to the frame's content pane
		contentPane.add(inputPanel, BorderLayout.CENTER);
		contentPane.add(boutonConnexion, BorderLayout.SOUTH);
		
		this.loginListener=new LoginListener(this);
		boutonConnexion.addActionListener(this.loginListener);
	}

	public String getMotDePasse() {
		return String.valueOf(motDePasseField.getPassword());
	}

	public String getNomUtilisateur() {
		// TODO Auto-generated method stub
		return nomUtilisateurField.getText();
	}

	public void setLoginListener(LoginListener listener) {
		this.loginListener = listener;
	}

	public void displayLoginFailureMessage() {
		JOptionPane.showMessageDialog(this, "Login failed. Please try again.");
	}
}
