package org.ensa.java.qcm.ui.exam;

import javax.swing.*;

import org.ensa.java.qcm.models.ResultatExamen;
import org.ensa.java.qcm.models.User;

import java.awt.*;
import java.util.Date;
import java.util.List;
import java.util.Vector;

public class ViewResultUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTabbedPane tabbedPane;
	private List<ResultatExamen> results;
	private User authUser;

	public ViewResultUI(List<ResultatExamen> results, User authUser) {
		this.authUser = authUser;
		this.results = results;
		initializeUI();
	}

	private void initializeUI() {
		setTitle("View Results");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setPreferredSize(new Dimension(800, 600));
		setLocationRelativeTo(null);

		tabbedPane = new JTabbedPane();
		
		if ("student".equals(authUser.getRole()))
			createStudentHistoryTab();
		else
			createProfessorResultsTab();

		add(tabbedPane);
		pack();
	}

	private void createStudentHistoryTab() {
		JPanel studentHistoryPanel = new JPanel(new BorderLayout());
		tabbedPane.addTab("Tests History", studentHistoryPanel);

		JTable studentTable = createSampleStudentTable();
		JScrollPane scrollPane = new JScrollPane(studentTable);

		studentHistoryPanel.add(scrollPane, BorderLayout.CENTER);
	}

	private JTable createSampleStudentTable() {
		Vector<String> columnNames = new Vector<>();
		columnNames.add("Student Name");
		columnNames.add("Test Date");
		columnNames.add("QCM");
		columnNames.add("Score");

		Vector<Vector<String>> data = new Vector<>();
		for (ResultatExamen rs : results) {
			Vector<String> row = new Vector<>();
			row.add(rs.getEtudiant().getNom());
			row.add(rs.getQCM().getQCMDate() != null ? rs.getQCM().getQCMDate().toString() : new Date().toString());
			row.add(rs.getQCM().getTitre());
			row.add(String.valueOf(rs.getScore()));
			data.add(row);
		}

		return new JTable(data, columnNames);
	}

	private void createProfessorResultsTab() {
		JPanel professorResultsPanel = new JPanel(new BorderLayout());
		tabbedPane.addTab("Passed Tests", professorResultsPanel);

		JTable professorTable = createSampleProfessorTable();
		JScrollPane scrollPane = new JScrollPane(professorTable);

		professorResultsPanel.add(scrollPane, BorderLayout.CENTER);
	}

	private JTable createSampleProfessorTable() {
		Vector<String> columnNames = new Vector<>();
		columnNames.add("Créateur");
		columnNames.add("Student Name");
		columnNames.add("Filière");
		columnNames.add("Niveau");
		columnNames.add("Test Date");
		columnNames.add("QCM");
		columnNames.add("Score");

		Vector<Vector<String>> data = new Vector<>();
		for (ResultatExamen rs : results) {
			Vector<String> row = new Vector<>();
			row.add(rs.getQCM().getProfesseur().getNom());
			row.add(rs.getEtudiant().getNom());
			row.add(rs.getEtudiant().getFiliere());
			row.add(rs.getEtudiant().getNiveau());
			row.add(rs.getQCM().getQCMDate() != null ? rs.getQCM().getQCMDate().toString() : new Date().toString());
			row.add(rs.getQCM().getTitre());
			row.add(String.valueOf(rs.getScore()));
			data.add(row);
		}

		return new JTable(data, columnNames);
	}

}
