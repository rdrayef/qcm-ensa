package org.ensa.java.qcm.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.ensa.java.qcm.models.User;
import org.ensa.java.qcm.ui.listeners.StartExamListner;
import org.ensa.java.qcm.ui.listeners.ViewResultListener;

public class QCMAppUI extends JFrame {

	private static final long serialVersionUID = 1L;

	private JButton startExamButton;
	private JButton viewResultsButton;

	private User authUser;

	private StartExamListner examListner;
	private ViewResultListener resultListner;

	public QCMAppUI(User authUser) {
		this.authUser = authUser;
		System.out.println("<<<<<<<<<" + authUser.getId());
		setTitle("QCM Application");
		setSize(400, 200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		// Create buttons
		startExamButton = new JButton("Start Exam");
		viewResultsButton = new JButton("View Results");

		// Create a panel for the buttons
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(2, 1));
		buttonPanel.add(startExamButton);
		buttonPanel.add(viewResultsButton);

		// Add the button panel to the main frame
		add(buttonPanel, BorderLayout.CENTER);

		// Add action listeners for the buttons
		this.examListner = new StartExamListner(this);
		startExamButton.addActionListener(examListner);

		this.resultListner=new ViewResultListener(this);
		viewResultsButton.addActionListener(resultListner);

		if (!"student".equals(authUser.getRole()))
			startExamButton.setEnabled(false);
	}

	public User getAuthUser() {
		return authUser;
	}

}
