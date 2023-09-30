package org.ensa.java.qcm.models;

public class ResultatExamen {
	private int id;
	private Etudiant etudiant;
	private QCM qcm;
	private int score;

	public ResultatExamen(int id, Etudiant etudiant, QCM qcm, int score) {
		this.id = id;
		this.etudiant = etudiant;
		this.qcm = qcm;
		this.score = score;
	}

	public ResultatExamen(Etudiant etudiant, QCM qcm, int score) {
		this.etudiant = etudiant;
		this.qcm = qcm;
		this.score = score;
	}

	public int getId() {
		return id;
	}

	public Etudiant getEtudiant() {
		return etudiant;
	}

	public QCM getQCM() {
		return qcm;
	}

	public int getScore() {
		return score;
	}
}
